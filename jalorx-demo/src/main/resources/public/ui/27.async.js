(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[27],{"t/hC":function(e,t,a){"use strict";var l=a("TqRt"),r=a("284h");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("14J3");var d=l(a("BMrR"));a("jCWc");var n=l(a("kPKH"));a("IzEo");var c=l(a("bx4M"));a("Mwp2");var u=l(a("VXEj"));a("Telt");var o,s,i=l(a("Tckk")),f=l(a("lwsE")),m=l(a("W8MJ")),p=l(a("a1gu")),E=l(a("Nsbk")),g=l(a("7W2i")),h=r(a("q1tI")),v=l(a("wd/R")),b=a("MuoO"),k=l(a("mOP9")),w=a("KTCi"),y=l(a("bjO7")),N=l(a("zHco")),j=l(a("wnz0")),C=[{title:"\u64cd\u4f5c\u4e00",href:""},{title:"\u64cd\u4f5c\u4e8c",href:""},{title:"\u64cd\u4f5c\u4e09",href:""},{title:"\u64cd\u4f5c\u56db",href:""},{title:"\u64cd\u4f5c\u4e94",href:""},{title:"\u64cd\u4f5c\u516d",href:""}],L=(o=(0,b.connect)(function(e){var t=e.user,a=e.project,l=e.activities,r=e.chart,d=e.loading;return{currentUser:t.currentUser,project:a,activities:l,chart:r,currentUserLoading:d.effects["user/fetchCurrent"],projectLoading:d.effects["project/fetchNotice"],activitiesLoading:d.effects["activities/fetchList"]}}),o(s=function(e){function t(){return(0,f.default)(this,t),(0,p.default)(this,(0,E.default)(t).apply(this,arguments))}return(0,g.default)(t,e),(0,m.default)(t,[{key:"componentDidMount",value:function(){var e=this.props.dispatch;e({type:"user/fetchCurrent"}),e({type:"project/fetchNotice"}),e({type:"activities/fetchList"}),e({type:"chart/fetch"})}},{key:"componentWillUnmount",value:function(){var e=this.props.dispatch;e({type:"chart/clear"})}},{key:"renderActivities",value:function(){var e=this.props.activities.list;return e.map(function(e){var t=e.template.split(/@\{([^{}]*)\}/gi).map(function(t){return e[t]?h.default.createElement("a",{href:e[t].link,key:e[t].name},e[t].name):t});return h.default.createElement(u.default.Item,{key:e.id},h.default.createElement(u.default.Item.Meta,{avatar:h.default.createElement(i.default,{src:e.user.avatar}),title:h.default.createElement("span",null,h.default.createElement("a",{className:j.default.username},e.user.name),"\xa0",h.default.createElement("span",{className:j.default.event},t)),description:h.default.createElement("span",{className:j.default.datetime,title:e.updatedAt},(0,v.default)(e.updatedAt).fromNow())}))})}},{key:"render",value:function(){var e=this.props,t=e.currentUser,a=e.currentUserLoading,l=e.project.notice,r=e.projectLoading,o=e.activitiesLoading,s=e.chart.radarData,f=t&&Object.keys(t).length?h.default.createElement("div",{className:j.default.pageHeaderContent},h.default.createElement("div",{className:j.default.avatar},h.default.createElement(i.default,{size:"large",src:t.avatar})),h.default.createElement("div",{className:j.default.content},h.default.createElement("div",{className:j.default.contentTitle},"\u65e9\u5b89\uff0c",t.name,"\uff0c\u795d\u4f60\u5f00\u5fc3\u6bcf\u4e00\u5929\uff01"),h.default.createElement("div",null,t.title," |",t.group))):null,m=h.default.createElement("div",{className:j.default.extraContent},h.default.createElement("div",{className:j.default.statItem},h.default.createElement("p",null,"\u9879\u76ee\u6570"),h.default.createElement("p",null,"56")),h.default.createElement("div",{className:j.default.statItem},h.default.createElement("p",null,"\u56e2\u961f\u5185\u6392\u540d"),h.default.createElement("p",null,"8",h.default.createElement("span",null," / 24"))),h.default.createElement("div",{className:j.default.statItem},h.default.createElement("p",null,"\u9879\u76ee\u8bbf\u95ee"),h.default.createElement("p",null,"2,223")));return h.default.createElement(N.default,{loading:a,content:f,extraContent:m},h.default.createElement(d.default,{gutter:24},h.default.createElement(n.default,{xl:16,lg:24,md:24,sm:24,xs:24},h.default.createElement(c.default,{className:j.default.projectList,style:{marginBottom:24},title:"\u8fdb\u884c\u4e2d\u7684\u9879\u76ee",bordered:!1,extra:h.default.createElement(k.default,{to:"/"},"\u5168\u90e8\u9879\u76ee"),loading:r,bodyStyle:{padding:0}},l.map(function(e){return h.default.createElement(c.default.Grid,{className:j.default.projectGrid,key:e.id},h.default.createElement(c.default,{bodyStyle:{padding:0},bordered:!1},h.default.createElement(c.default.Meta,{title:h.default.createElement("div",{className:j.default.cardTitle},h.default.createElement(i.default,{size:"small",src:e.logo}),h.default.createElement(k.default,{to:e.href},e.title)),description:e.description}),h.default.createElement("div",{className:j.default.projectItemContent},h.default.createElement(k.default,{to:e.memberLink},e.member||""),e.updatedAt&&h.default.createElement("span",{className:j.default.datetime,title:e.updatedAt},(0,v.default)(e.updatedAt).fromNow()))))})),h.default.createElement(c.default,{bodyStyle:{padding:0},bordered:!1,className:j.default.activeCard,title:"\u52a8\u6001",loading:o},h.default.createElement(u.default,{loading:o,size:"large"},h.default.createElement("div",{className:j.default.activitiesList},this.renderActivities())))),h.default.createElement(n.default,{xl:8,lg:24,md:24,sm:24,xs:24},h.default.createElement(c.default,{style:{marginBottom:24},title:"\u5feb\u901f\u5f00\u59cb / \u4fbf\u6377\u5bfc\u822a",bordered:!1,bodyStyle:{padding:0}},h.default.createElement(y.default,{onAdd:function(){},links:C,linkElement:k.default})),h.default.createElement(c.default,{style:{marginBottom:24},bordered:!1,title:"XX \u6307\u6570",loading:0===s.length},h.default.createElement("div",{className:j.default.chart},h.default.createElement(w.Radar,{hasLegend:!0,height:343,data:s}))),h.default.createElement(c.default,{bodyStyle:{paddingTop:12,paddingBottom:12},bordered:!1,title:"\u56e2\u961f",loading:r},h.default.createElement("div",{className:j.default.members},h.default.createElement(d.default,{gutter:48},l.map(function(e){return h.default.createElement(n.default,{span:12,key:"members-item-".concat(e.id)},h.default.createElement(k.default,{to:e.href},h.default.createElement(i.default,{src:e.logo,size:"small"}),h.default.createElement("span",{className:j.default.member},e.member)))})))))))}}]),t}(h.PureComponent))||s),I=L;t.default=I},wnz0:function(e,t,a){e.exports={activitiesList:"antd-pro-pages-dashboard-workplace-activitiesList",username:"antd-pro-pages-dashboard-workplace-username",event:"antd-pro-pages-dashboard-workplace-event",pageHeaderContent:"antd-pro-pages-dashboard-workplace-pageHeaderContent",avatar:"antd-pro-pages-dashboard-workplace-avatar",content:"antd-pro-pages-dashboard-workplace-content",contentTitle:"antd-pro-pages-dashboard-workplace-contentTitle",extraContent:"antd-pro-pages-dashboard-workplace-extraContent",statItem:"antd-pro-pages-dashboard-workplace-statItem",members:"antd-pro-pages-dashboard-workplace-members",member:"antd-pro-pages-dashboard-workplace-member",projectList:"antd-pro-pages-dashboard-workplace-projectList",cardTitle:"antd-pro-pages-dashboard-workplace-cardTitle",projectGrid:"antd-pro-pages-dashboard-workplace-projectGrid",projectItemContent:"antd-pro-pages-dashboard-workplace-projectItemContent",datetime:"antd-pro-pages-dashboard-workplace-datetime",activeCard:"antd-pro-pages-dashboard-workplace-activeCard"}}}]);