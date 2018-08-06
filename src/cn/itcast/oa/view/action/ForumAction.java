package cn.itcast.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.awt.geom.AreaOp.AddOp;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum>{
	
	/**
	 * 0表示查看全部主题
	 * 1表示只看精华帖
	 */
	private int viewType = 0;
	
	/**
	 * 0表示默认排序（按最后更新时间排序，但所有置顶帖都在前面）
	 * 1表示按最后更新时间排序
	 * 2表示按主题发表时间排序
	 * 3表示按回复数量排序
	 * 
	 */
	private int orderBy = 0;
	
	/**
	 * true表示升序
	 * false表示降序
	 */
	private boolean asc = false;
	
	/*
	 * 版块列表
	 */
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	/*
	 * 显示单个板块（主题列表）
	 */
	public String show() throws Exception {
		//准备forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		
//		//准备topic
//		PageBean pageBean = topicService.getPageBeanByForum(pageNum ,pageSize ,forum);
//		ActionContext.getContext().getValueStack().push(pageBean);
		
//		String hql = "FROM Topic t WHERE t.forum=?";
//		List<Object> parameters = new ArrayList<Object>();
//		parameters.add(forum);
//		
//		if(viewType == 1){
//			hql +=" AND t.type=?";
//			parameters.add(Topic.TYPE_BEST);
//		}
//		
//		if(orderBy == 1){
//			//1表示按最后更新时间排序
//			hql += " ORDER BY t.lastUpdateTime " + (asc ? "ASC" : "DESC");
//		}else if(orderBy == 2){
//			//2表示按主题发表时间排序
//			hql += " ORDER BY t.postTime " + (asc ? "ASC" : "DESC");
//		}else if(orderBy == 3){
//			//3表示按回复数量排序
//			hql += " ORDER BY t.replyCount " + (asc ? "ASC" : "DESC");
//		}else{
//			//0表示默认排序（按最后更新时间排序，但所有置顶帖都在前面）
//			hql += " ORDER BY(CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC";
//		}
//		
//		
//		PageBean pageBean = replyService.getPageBean(pageNum ,pageSize ,hql ,parameters);
//		ActionContext.getContext().getValueStack().push(pageBean);
		
		new QueryHelper(Topic.class, "t")
				.addCondition("t.forum=?", forum)
				.addCondition(viewType == 1, "t.type=?", Topic.TYPE_BEST)
				.addOrderProperty(orderBy == 1, "t.lastUpdateTime", asc)
				.addOrderProperty(orderBy == 2, "t.postTime", asc)
				.addOrderProperty(orderBy == 3, "t.replyCount", asc)
				.addOrderProperty(orderBy == 0, "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", asc)
				.preparePageBean(topicService, pageNum, pageSize);
		
		return "show";
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}



}
