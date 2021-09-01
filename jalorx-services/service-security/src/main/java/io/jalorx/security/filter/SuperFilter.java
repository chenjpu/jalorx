package io.jalorx.security.filter;

import jakarta.inject.Inject;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.utils.AuthInfoUtils;
import io.jalorx.security.entity.Action;
import io.jalorx.security.service.ActionService;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.http.HttpAttributes;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.FilterChain;
import io.micronaut.http.filter.HttpFilter;
import io.micronaut.security.rules.SensitiveEndpointRule;
import io.micronaut.web.router.RouteMatch;

@Filter("/**")
public class SuperFilter implements HttpFilter {

  @Inject
  ActionService service;

//  @Value("${logLevel}")
//  protected String logLevel;
//  
  @Inject
  protected Environment evn;

  private static Logger LOG = LoggerFactory.getLogger(SuperFilter.class);

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(HttpRequest<?> request, FilterChain chain)
      throws BusinessAccessException {
    String path = request.getPath();
    String ip = request.getRemoteAddress().getHostString();
    RouteMatch<?> routeMatch =
        request.getAttribute(HttpAttributes.ROUTE_MATCH, RouteMatch.class).orElse(null);
    String logLevel = evn.getProperty("loglevel", String.class, "0");
    if (path.contains("login") || path.contains("logout")) {
      String[] descArr = path.split("/");
      String desc = descArr[descArr.length - 1];

      if ("login".equals(desc)) {
        if (logLevel != null && logLevel.equals("1")) {
          LOG.debug("操作日志:" + "系统登录" + "  IP：" + ip + "  访问路径：" + path);
        } else if (logLevel != null && logLevel.equals("2")) {
          Action action = new Action();
          action.setOperDesc("系统登录");
          action.setResDesc("login");
          action.setIpAddress(ip);
          action.setMethodName(path);
          action.setCreateUserId("");
          service.save(action);
        }
      } else {
        if (logLevel != null && logLevel.equals("1")) {
          LOG.debug("操作日志:" + "退出登录" + "  IP：" + ip + "  访问路径：" + path);
        } else if (logLevel != null && logLevel.equals("2")) {
          Action action = new Action();
          action.setOperDesc("退出登录");
          action.setResDesc("loginOut");
          action.setIpAddress(ip);
          action.setMethodName(path);
          action.setCreateUserId("");
          service.save(action);
        }
      }
    } else {
      try {
        AnnotationValue<Resource> res = routeMatch.findAnnotation(Resource.class).orElse(null);
        AnnotationValue<Operation> op = routeMatch.findAnnotation(Operation.class).orElse(null);
        int resCode = res.getRequiredValue(Integer.class);
        String resDesc = res.getRequiredValue("desc", String.class);
        int operCode = op.getRequiredValue(Integer.class);
        String operDesc = op.getRequiredValue("desc", String.class);
        if (logLevel != null && logLevel.equals("1")) {
          LOG.debug("操作日志:" + "  操作码：" + operCode + "  操作描述：" + operDesc + "  资源码：" + resCode
              + "  资源描述：" + resDesc + "  IP：" + ip + "  访问路径：" + path);
        } else if (logLevel != null && logLevel.equals("2")) {
          Action action = new Action();
          action.setOperCode(operCode);
          action.setOperDesc(operDesc);
          action.setResCode(resCode);
          action.setResDesc(resDesc);
          action.setIpAddress(ip);
          action.setMethodName(path);
          action.setCreateUserId(AuthInfoUtils.getCurrentUserId() + "");
          service.save(action);
        }
      } catch (Exception e) {
        LOG.debug("操作日志:未捕获到资源码或者描述,日志写入异常");
      }

    }
    return chain.proceed(request);
  }

  public int getOrder() {
    return SensitiveEndpointRule.ORDER + 6;
  }
}
