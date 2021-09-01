package io.jalorx.boot.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jalorx.boot.Account;
import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.RowRule;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.ServerAuthentication;

/**
 * @author chenb
 */
@Introspected
public class SimpleAuthInfo extends ServerAuthentication implements AuthInfo,AuthenticationResponse, Serializable {

	private static final long serialVersionUID = 8875138309999956798L;

	private static final String ROOT_ROLE = "1-1";

	private Collection<String>   roles       = Collections.emptySet();
	private Map<String, RowRule> rowRules    = Collections.emptyMap();
	private Collection<String>   permissions = Collections.emptySet();
	private long                 userId      = -1;
	private String               acount;
	private String               email;
	private String               currentRole = "";
	private Map<String, String>  attrs       = new HashMap<>();
	// 密码校验需要用到当前登录的密码值？？？
	private String password;
	private String language;
	private String firstname;
	private String lastname;
	private String orginfo;
	private String tenantId;
	private String appName;
	private String appScope;
	private String backUp;
	private String backUp1;
	private String backUp2;

	public SimpleAuthInfo() {
		super("", Collections.emptyList(),null);
	}

	public SimpleAuthInfo(Collection<String> roles, Account user) {
		super(user.getAcount(), roles,null);
		this.roles     = roles;
		this.userId    = user.getId();
		this.acount    = user.getAcount();
		this.email     = user.getEmail();
		this.password  = user.getPassword();
		this.language  = user.getLanguageCode();
		this.firstname = user.getFirstname();
		this.lastname  = user.getLastname();
		this.orginfo   = user.getOrginfo();
		this.tenantId  = user.getTenantId();
		this.appName   = user.getAppName();
		this.appScope  = user.getAppScope();
		this.backUp    = user.getBackUp();
		this.backUp1   = user.getBackUp1();
		this.backUp2   = user.getBackUp2();
	}

	@Override
	public Collection<String> getRoles() {
		return roles;
	}

	@Override
	public boolean isRoleMerged() {
		return false;
	}

	@Override
	public String getCurrentRole() {
		return currentRole;
	}

	@Override
	public boolean isPermitted(Serializable prefix) {
		if (isRoot()) {
			return true;
		}
		return permissions.contains(prefix);
	}

	@Override
	public long getUserId() {
		return userId;
	}

	@Override
	public String getAccount() {
		return acount;
	}

	@Override
	public Collection<String> getPermissions() {
		return this.permissions;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public String getAttr(String name) {
		return attrs.get(name);
	}

	/**
	 * @return the firstname
	 */
	@Override
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the lastname
	 */
	@Override
	public String getLastname() {
		return lastname;
	}

	@Override
	public boolean validatePwd(String pwd) {
		return StringUtils.equals(pwd, password);
	}

	@Override
	public String getOrginfo() {
		return orginfo;
	}

	@Override
	public boolean isRoot() {
		return ROOT_ROLE.equals(this.currentRole) || this.userId == 0;
	}

	@Override
	public boolean isDataALL() {
		return StringUtils.endsWith(this.currentRole, "-1") || this.userId == 0;
	}

	@Override
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public String getAppName() {
		return appName;
	}

	@Override
	public String getAppScope() {
		return appScope;
	}

	/**
	 * @return the acount
	 */
	public String getAcount() {
		return acount;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @param acount the acount to set
	 */
	public void setAcount(String acount) {
		this.acount = acount;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @param orginfo the orginfo to set
	 */
	public void setOrginfo(String orginfo) {
		this.orginfo = orginfo;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @param appScope the appScope to set
	 */
	public void setAppScope(String appScope) {
		this.appScope = appScope;
	}

	@Override
	public String getBackUp() {
		return this.backUp;
	}

	@Override
	public String getBackUp1() {
		return this.backUp1;
	}

	@Override
	public String getBackUp2() {
		return this.backUp2;
	}

	@Override
	public String getStringUserId() {
		return String.valueOf(userId);
	}

	@Override
	@JsonIgnore
	public Optional<Authentication> getAuthentication() {
		return Optional.of(this);
	}

	@Override
	public RowRule getRowRule(String id) {
		return rowRules.getOrDefault(id, RowRule.DENY_ALL);
	}

	@Override
	public Collection<String> getColRule(String id) {
		return Collections.emptySet();
	}

	@Override
	public void switchRole(String id) {
		this.currentRole = id;
	}

	public Map<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}

	@Override
	public String setAttr(String name, String value) {
		return this.attrs.put(name, value);
	}

	public void setRowRules(Collection<RowRule> rowRules) {
		Map<String, RowRule> rules = new HashMap<>();
		rowRules.forEach(r -> {
			rules.put(r.getCode(), r);
		});
		this.rowRules = rules;
	}

	public void setPermissions(Collection<String> permissions) {
		this.permissions = permissions;
	}

	public Collection<RowRule> getRowRules() {
		return rowRules.values();
	}
}
