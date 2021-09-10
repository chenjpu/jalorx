package io.jalorx.security.entity;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jalorx.boot.TreeNode;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.model.LongIdVO;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 组织.
 * 
 * @author chenb
 */
@Introspected
@Validated
@MappedEntity("tpl_org_t")
public class Org extends LongIdVO {

	private static final long serialVersionUID = -6679836646467088231L;
	@Schema(title = "父级ID")
	private Long pId;
	@NotEmpty
	@Schema(title = "机构名称")
	private String orgName;
	@NotEmpty
	@Schema(title = "机构状态")
	private String orgCode;
	@Schema(title = "部门节点")
	private String orgNote;
	@Schema(title = "备用字段1")
	private String filed1;
	@Schema(title = "备用字段2")
	private String filed2;
	@Schema(title = "是否叶子节点")
	@Transient
	private boolean isLeaf;

	public Long getPId() {
		return pId;
	}

	public void setPId(Long pId) {
		this.pId = pId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgNote() {
		return orgNote;
	}

	public void setOrgNote(String orgNote) {
		this.orgNote = orgNote;
	}

	public String getFiled1() {
		return filed1;
	}

	public void setFiled1(String filed1) {
		this.filed1 = filed1;
	}

	public String getFiled2() {
		return filed2;
	}

	public void setFiled2(String filed2) {
		this.filed2 = filed2;
	}
	@Transient
	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	@JsonIgnore
	public TreeNode treeof() {
		TreeNode tn = new TreeNode();
		tn.setId(String.valueOf(getId()));
		tn.setCode(orgCode);
		tn.setLabel(orgName);
		tn.setParentId(String.valueOf(pId));
		return tn;
	}

	@JsonIgnore
	public Meta metaof() {
		Meta m = new Meta(this.getId());
		m.id = this.getId();
		m.orgName = this.orgName;
		m.orgCode = this.orgCode;
		return m;
	}

	@Introspected
	public static class Meta implements Id<Long> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6904528597631151959L;
		@Schema(title = "主键")
		private Long id;
		@Schema(title = "机构名称")
		private String orgName;
		@Schema(title = "机构状态")
		private String orgCode;

		public Meta(Long id) {
			this.id = id;
		}

		@Override
		public Long getId() {
			return id;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getOrgCode() {
			return orgCode;
		}

		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}
}
