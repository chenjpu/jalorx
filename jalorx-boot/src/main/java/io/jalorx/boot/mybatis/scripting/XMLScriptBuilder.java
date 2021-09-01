/*
 * Copyright 2009-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.jalorx.boot.mybatis.scripting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.apache.ibatis.session.Configuration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.jalorx.boot.mybatis.mapping.ExtDynamicSqlSource;
import io.jalorx.boot.mybatis.mapping.ExtRawSqlSource;
import io.jalorx.boot.mybatis.mapping.PageSqlSource;
import io.jalorx.boot.mybatis.scripting.xmltags.QuerySqlNode;

public class XMLScriptBuilder extends BaseBuilder {

	// 分页，自定义
	private final static String PAGEABLE = "pageable";

	private XNode    context;
	private boolean  isDynamic;
	private Class<?> parameterType;

	private Map<String, NodeHandler> nodeHandlers = new HashMap<String, NodeHandler>();

	{
		nodeHandlers.put("trim", new TrimHandler());
		nodeHandlers.put("where", new WhereHandler());
		nodeHandlers.put("set", new SetHandler());
		nodeHandlers.put("foreach", new ForEachHandler());
		nodeHandlers.put("if", new IfHandler());
		nodeHandlers.put("choose", new ChooseHandler());
		nodeHandlers.put("when", new IfHandler());
		nodeHandlers.put("otherwise", new OtherwiseHandler());
		nodeHandlers.put("bind", new BindHandler());
		nodeHandlers.put("filter", new QueryHandler());
	}

	NodeHandler nodeHandlers(String nodeName) {
		return nodeHandlers.get(nodeName);
	}

	public XMLScriptBuilder(Configuration configuration, XNode context) {
		this(configuration, context, null);
	}

	public XMLScriptBuilder(Configuration configuration, XNode context, Class<?> parameterType) {
		super(configuration);
		this.context       = context;
		this.parameterType = parameterType;
	}

	public SqlSource parseScriptNode() {
		List<SqlNode> contents           = parseDynamicTags(context);
		MixedSqlNode  rootSqlNode        = new MixedSqlNode(contents);
		SqlSource     sqlSource          = null;

		boolean pageable = context.getBooleanAttribute(PAGEABLE, false);

		if (pageable) {
			return new PageSqlSource(configuration, rootSqlNode, isDynamic);
		} else if (isDynamic) {
			sqlSource = new ExtDynamicSqlSource(configuration, rootSqlNode);
		} else {
			sqlSource = new ExtRawSqlSource(configuration, rootSqlNode, parameterType);
		}
		return sqlSource;
	}

	List<SqlNode> parseDynamicTags(XNode node) {
		List<SqlNode> contents = new ArrayList<SqlNode>();
		NodeList      children = node.getNode()
				.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			XNode child = node.newXNode(children.item(i));
			if (child.getNode()
					.getNodeType() == Node.CDATA_SECTION_NODE
					|| child.getNode()
							.getNodeType() == Node.TEXT_NODE) {
				String      data        = child.getStringBody("");
				TextSqlNode textSqlNode = new TextSqlNode(data);
				if (textSqlNode.isDynamic()) {
					contents.add(textSqlNode);
					isDynamic = true;
				} else {
					contents.add(new StaticTextSqlNode(data));
				}
			} else if (child.getNode()
					.getNodeType() == Node.ELEMENT_NODE) { // issue #628
				String      nodeName = child.getNode()
						.getNodeName();
				NodeHandler handler  = nodeHandlers(nodeName);
				if (handler == null) {
					throw new BuilderException("Unknown element <" + nodeName + "> in SQL statement.");
				}
				handler.handleNode(child, contents);
				isDynamic = true;
			}
		}
		return contents;
	}

	private interface NodeHandler {
		void handleNode(XNode nodeToHandle, List<SqlNode> targetContents);
	}

	private class BindHandler implements NodeHandler {
		public BindHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			final String         name       = nodeToHandle.getStringAttribute("name");
			final String         expression = nodeToHandle.getStringAttribute("value");
			final VarDeclSqlNode node       = new VarDeclSqlNode(name, expression);
			targetContents.add(node);
		}
	}

	private class TrimHandler implements NodeHandler {
		public TrimHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents        = parseDynamicTags(nodeToHandle);
			MixedSqlNode  mixedSqlNode    = new MixedSqlNode(contents);
			String        prefix          = nodeToHandle.getStringAttribute("prefix");
			String        prefixOverrides = nodeToHandle.getStringAttribute("prefixOverrides");
			String        suffix          = nodeToHandle.getStringAttribute("suffix");
			String        suffixOverrides = nodeToHandle.getStringAttribute("suffixOverrides");
			TrimSqlNode   trim            = new TrimSqlNode(configuration, mixedSqlNode, prefix, prefixOverrides,
					suffix, suffixOverrides);
			targetContents.add(trim);
		}
	}

	private class WhereHandler implements NodeHandler {
		public WhereHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents     = parseDynamicTags(nodeToHandle);
			MixedSqlNode  mixedSqlNode = new MixedSqlNode(contents);
			WhereSqlNode  where        = new WhereSqlNode(configuration, mixedSqlNode);
			targetContents.add(where);
		}
	}

	private class SetHandler implements NodeHandler {
		public SetHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents     = parseDynamicTags(nodeToHandle);
			MixedSqlNode  mixedSqlNode = new MixedSqlNode(contents);
			SetSqlNode    set          = new SetSqlNode(configuration, mixedSqlNode);
			targetContents.add(set);
		}
	}

	private class ForEachHandler implements NodeHandler {
		public ForEachHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode>  contents       = parseDynamicTags(nodeToHandle);
			MixedSqlNode   mixedSqlNode   = new MixedSqlNode(contents);
			String         collection     = nodeToHandle.getStringAttribute("collection");
			String         item           = nodeToHandle.getStringAttribute("item");
			String         index          = nodeToHandle.getStringAttribute("index");
			String         open           = nodeToHandle.getStringAttribute("open");
			String         close          = nodeToHandle.getStringAttribute("close");
			String         separator      = nodeToHandle.getStringAttribute("separator");
			ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, mixedSqlNode, collection,
					index, item, open, close, separator);
			targetContents.add(forEachSqlNode);
		}
	}

	private class IfHandler implements NodeHandler {
		public IfHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents     = parseDynamicTags(nodeToHandle);
			MixedSqlNode  mixedSqlNode = new MixedSqlNode(contents);
			String        test         = nodeToHandle.getStringAttribute("test");
			IfSqlNode     ifSqlNode    = new IfSqlNode(mixedSqlNode, test);
			targetContents.add(ifSqlNode);
		}
	}

	private class OtherwiseHandler implements NodeHandler {
		public OtherwiseHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents     = parseDynamicTags(nodeToHandle);
			MixedSqlNode  mixedSqlNode = new MixedSqlNode(contents);
			targetContents.add(mixedSqlNode);
		}
	}

	private class ChooseHandler implements NodeHandler {
		public ChooseHandler() {
			// Prevent Synthetic Access
		}

		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> whenSqlNodes      = new ArrayList<SqlNode>();
			List<SqlNode> otherwiseSqlNodes = new ArrayList<SqlNode>();
			handleWhenOtherwiseNodes(nodeToHandle, whenSqlNodes, otherwiseSqlNodes);
			SqlNode       defaultSqlNode = getDefaultSqlNode(otherwiseSqlNodes);
			ChooseSqlNode chooseSqlNode  = new ChooseSqlNode(whenSqlNodes, defaultSqlNode);
			targetContents.add(chooseSqlNode);
		}

		private void handleWhenOtherwiseNodes(XNode chooseSqlNode, List<SqlNode> ifSqlNodes,
				List<SqlNode> defaultSqlNodes) {
			List<XNode> children = chooseSqlNode.getChildren();
			for (XNode child : children) {
				String      nodeName = child.getNode()
						.getNodeName();
				NodeHandler handler  = nodeHandlers(nodeName);
				if (handler instanceof IfHandler) {
					handler.handleNode(child, ifSqlNodes);
				} else if (handler instanceof OtherwiseHandler) {
					handler.handleNode(child, defaultSqlNodes);
				}
			}
		}

		private SqlNode getDefaultSqlNode(List<SqlNode> defaultSqlNodes) {
			SqlNode defaultSqlNode = null;
			if (defaultSqlNodes.size() == 1) {
				defaultSqlNode = defaultSqlNodes.get(0);
			} else if (defaultSqlNodes.size() > 1) {
				throw new BuilderException("Too many default (otherwise) elements in choose statement.");
			}
			return defaultSqlNode;
		}
	}

	private class QueryHandler implements NodeHandler {
		@Override
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			String       open       = nodeToHandle.getStringAttribute("open");
			String       close      = nodeToHandle.getStringAttribute("close");
			String       expression = nodeToHandle.getStringAttribute("value", "query");
			String       filterName = nodeToHandle.getStringAttribute("name", "T");
			String       rule       = nodeToHandle.getStringAttribute("rule");
			boolean      tenant     = nodeToHandle.getBooleanAttribute("tenant", true);
			QuerySqlNode node       = new QuerySqlNode(open, close, expression, filterName, rule, tenant);
			targetContents.add(node);
		}
	}

}
