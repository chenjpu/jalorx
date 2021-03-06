package io.jalorx.security.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jalorx.boot.Account;
import io.jalorx.boot.annotation.Dep;
import io.jalorx.boot.annotation.Lookup;
import io.jalorx.boot.model.Id;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.data.annotation.event.PrePersist;
import io.micronaut.data.annotation.event.PreUpdate;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户.
 * 
 * @author chenb
 */
@Introspected
@Validated
@MappedEntity("tpl_user_t")
public class User implements Account, Id<Long> {

	private static final long serialVersionUID = 1757475622243875292L;

	@Schema(title = "主键ID")
	@GeneratedValue
	@io.micronaut.data.annotation.Id
	private Long id;

	@NotEmpty
	@Schema(title = "帐号")
	private String acount;
	@Schema(title = "邮箱")
	@Email
	private String email;
	@Schema(title = "姓")
	private String firstname;
	@NotEmpty
	@Schema(title = "名")
	private String lastname;
	@Lookup(type = "STATUS")
	@Schema(title = "状态")
	private short status;
	@Schema(title = "地址1")
	private String addr1;
	@Schema(title = "地址2")
	private String addr2;
	@Schema(title = "所在城市")
	private String city;
	@Schema(title = "洲市")
	private String state;
	@Schema(title = "邮政编码")
	private String zip;
	@Schema(title = "国家")
	private String country;
	@Schema(title = "电话")
	private String phone;
	@Lookup(type = "LANGUAGE")
	@NotEmpty
	@Schema(title = "语言")
	private String languageCode;

	@JsonIgnore
	@Schema(title = "密码")
	@AutoPopulated(updateable = false)
	protected String password;

	@JsonIgnore
	@Schema(title = "密码盐度")
	@Transient
	protected String passwordSalt;

	@JsonIgnore
	@Schema(title = "删除标识")
	@Transient
	private Short delFlag;

	@Dep
	@NotEmpty
	@Schema(title = "机构信息")
	private String orginfo;
	@Schema(title = "电话")
	@Transient
	protected String mobile;
	@Schema(title = "传真")
	@Transient
	protected String fax;
	@Schema(title = "地址")
	@Transient
	protected String address;
	@Schema(title = "相片")
	@Transient
	protected String photo;
	@JsonIgnore
	@Schema(title = "进入时间")
	@Transient
	protected Date accessionTime;
	@Schema(title = "教育程度")
	@Transient
	protected String education;
	@JsonIgnore
	@Transient
	protected Short title;
	@Schema(title = "系统内置标识0否1是")
	@Transient
	private boolean defaultIn;
	@Schema(title = "备用")
	private String backUp;
	@Schema(title = "备用1")
	private String backUp1;
	@Schema(title = "备用2")
	private String backUp2;

	@Schema(title = "创建时间")
	@AutoPopulated(updateable = false)
	private LocalDateTime createDate;
	@Schema(title = "最后修改时间")
	private LocalDateTime lastUpdateDate;

	@Schema(title = "项目名称")
	@AutoPopulated(updateable = false)
	private String appName;
	@Schema(title = "多租户id")
	@AutoPopulated(updateable = false)
	private String tenantId;
	@Schema(title = "项目群")
	@AutoPopulated(updateable = false)
	private String appScope;

	@Transient
	private Set<String> rights = new HashSet<String>();

	@Transient
	private Map<String, String> attrs = new HashMap<>();

	/**
	 * Default Empty Constructor for class AppUser.
	 */
	public User() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class AppUser.
	 */
	public User(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * * @return String
	 * 
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Set the password
	 * 
	 */
	public void setPassword(String aValue) {
		this.password = aValue;
	}

	/**
	 * * @return String
	 * 
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set the email.
	 * 
	 */
	public void setEmail(String aValue) {
		this.email = aValue;
	}

	/**
	 * @return String
	 * 
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Set the phone.
	 */
	public void setPhone(String aValue) {
		this.phone = aValue;
	}

	/**
	 * * @return String
	 * 
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * Set the mobile.
	 */
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}

	/**
	 * * @return String
	 * 
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * Set the fax.
	 */
	public void setFax(String aValue) {
		this.fax = aValue;
	}

	/**
	 * * @return String
	 * 
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set the address.
	 */
	public void setAddress(String aValue) {
		this.address = aValue;
	}

	/**
	 * * @return String
	 * 
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Set the zip.
	 */
	public void setZip(String aValue) {
		this.zip = aValue;
	}

	/**
	 * * @return String
	 * 
	 */
	public String getPhoto() {
		return this.photo;
	}

	/**
	 * Set the photo.
	 */
	public void setPhoto(String aValue) {
		this.photo = aValue;
	}

	/**
	 * * @return java.util.Date
	 * 
	 */
	public java.util.Date getAccessionTime() {
		return this.accessionTime;
	}

	/**
	 * Set the accessionTime.
	 * 
	 * public void setAccessionTime(java.util.Date aValue) { this.accessionTime =
	 * aValue; }
	 * 
	 * /** * @return String
	 * 
	 */
	public String getEducation() {
		return this.education;
	}

	/**
	 * Set the education.
	 */
	public void setEducation(String aValue) {
		this.education = aValue;
	}

	/**
	 * * @return Short
	 * 
	 */
	public Short getTitle() {
		return this.title;
	}

	/**
	 * Set the title.
	 */
	public void setTitle(Short aValue) {
		this.title = aValue;
	}

	@Transient
	public boolean isEnabled() {
		if (status == 1) {
			return true;
		}
		return false;
	}

	public Set<String> getRights() {
		return rights;
	}

	public Short getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Short delFlag) {
		this.delFlag = delFlag;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOrginfo() {
		return orginfo;
	}

	public void setOrginfo(String orginfo) {
		this.orginfo = orginfo;
	}

	public void setRights(Set<String> rights) {
		this.rights = rights;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the passwordSalt
	 */
	@JsonIgnore
	public String getPasswordSalt() {
		return passwordSalt;
	}

	/**
	 * @param passwordSalt the passwordSalt to set
	 */
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	@JsonIgnore
	@Transient
	public boolean isLocked() {
		return status == 0;
	}

	/**
	 * 是否超级用户
	 * 
	 * @return
	 */
	@JsonIgnore
	@Transient
	public boolean isRoot() {
		return getId() == 0;
	}

	@JsonIgnore
	@Transient
	public boolean isCredentialsExpired() {
		return this.status == 2;
	}

	public Map<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}

	public boolean isDefaultIn() {
		return defaultIn;
	}

	public void setDefaultIn(boolean defaultIn) {
		this.defaultIn = defaultIn;
	}

	public String getBackUp() {
		return backUp;
	}

	public String getBackUp1() {
		return backUp1;
	}

	public String getBackUp2() {
		return backUp2;
	}

	public void setBackUp(String backUp) {
		this.backUp = backUp;
	}

	public void setBackUp1(String backUp1) {
		this.backUp1 = backUp1;
	}

	public void setBackUp2(String backUp2) {
		this.backUp2 = backUp2;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAppScope() {
		return appScope;
	}

	public void setAppScope(String appScope) {
		this.appScope = appScope;
	}
	
	@PrePersist
	public void createInit() {
		this.createDate = LocalDateTime.now();
		this.lastUpdateDate = LocalDateTime.now();
		this.appName = "local";
		this.appScope = "local";
		this.tenantId = "global";
	}

	@PreUpdate
	public void updateInit() {
		this.lastUpdateDate = LocalDateTime.now();
	}

	@JsonIgnore
	public Meta metaof() {
		Meta m = new Meta(this.getId());
		m.acount = this.acount;
		m.email = this.email;
		m.firstname = this.firstname;
		m.lastname = this.lastname;
		return m;
	}

	@Introspected
	public static class Meta implements Id<Long> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4659554182062186588L;
		@Schema(title = "主键")
		private Long id;
		@Schema(title = "账户")
		private String acount;
		@Schema(title = "邮件")
		private String email;
		@Schema(title = "姓")
		private String firstname;
		@Schema(title = "名")
		private String lastname;

		public Meta(Long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getAcount() {
			return acount;
		}

		public void setAcount(String acount) {
			this.acount = acount;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
	}
}
