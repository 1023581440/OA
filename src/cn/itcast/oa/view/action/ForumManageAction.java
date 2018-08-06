package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;

@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum>{

	/**
	 * 列表
	 * @return
	 */	
	public String list() throws Exception{
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";

	}
	/**
	 * 删除
	 * @return
	 */
	public String delete() throws Exception{
		forumService.delete(model.getId());
		return "toList";

	}
	/**
	 * 添加页面
	 * @return
	 */
	public String addUI() throws Exception{
		return "saveUI";

	}
	/**
	 * 添加
	 * @return
	 */
	public String add() throws Exception{
		forumService.save(model);
		return "toList";

	}
	/**
	 * 修改页面
	 * @return
	 */
	public String editUI() throws Exception{
		//准备回显的数据
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";

	}
	/**
	 * 修改
	 * @return
	 */
	public String edit() throws Exception{
		Forum forum = forumService.getById(model.getId());
		
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		
		forumService.update(forum);
		
 		return "toList";

	}
	/**
	 * 上移
	 * @return
	 */
	public String moveUp() throws Exception{
		forumService.moveUp(model.getId());
		return "toList";

	}
	/**
	 * 下移
	 * @return
	 */
	public String moveDown() throws Exception{
		forumService.moveDown(model.getId());
		return "toList";

	}

}
