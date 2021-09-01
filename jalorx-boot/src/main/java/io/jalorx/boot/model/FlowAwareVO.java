/**
 * 
 */
package io.jalorx.boot.model;

import java.util.Map;

import io.jalorx.boot.Flow;
import io.jalorx.boot.FlowAware;

/**
 * @author chenjpu
 *
 */
public class FlowAwareVO extends BaseFlowAwareVO implements FlowAware {
	/**
	* 
	*/
	private static final long   serialVersionUID = 3022428893263444366L;
	private Flow                flow;
	private Long                dfId;
	private Map<String, Object> variables;

	protected void ensureFlowInitialized() {
		if ((flow == null)) {
			flow = new Flow();
		}
	}

	public final void setProcessName(String processName) {
		ensureFlowInitialized();
		flow.setProcessName(processName);
	}

	public final void setProcessStatus(String processStatus) {
		ensureFlowInitialized();
		flow.setProcessStatus(processStatus);
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public Long getDfId() {
		return dfId;
	}

	public void setDfId(Long dfId) {
		this.dfId = dfId;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
