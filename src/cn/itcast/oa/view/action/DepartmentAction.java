package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department> {

	private Long parentId;

	/** 列表 */
	public String list() throws Exception {
		List<Department> departmentList = null;
		if (parentId == null) { // 顶级部门列表
			departmentList = departmentService.findTopList();
		} else { // 子部门列表
			departmentList = departmentService.findChildren(parentId);
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		departmentService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		// 准备数据, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		// 封装信息到对象中
		// Department department = new Department();
		// department.setName(name);
		// department.setDescription(description)
		Department parent = departmentService.getById(parentId);
		model.setParent(parent);

		// 保存
		departmentService.save(model);

		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备数据, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 准备回显的数据
		Department department = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		if (department.getParent() != null) {
			parentId = department.getParent().getId();
		}
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		//从数据库取出原对象
		Department department = null;
		if(parentId !=null){//拿出对象
			department = departmentService.getById(model.getId());
		}else{//删除原先的对象，创建新对象
			departmentService.delete(model.getId());
			department = new Department();
		}
		//设置属性
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		if(parentId !=null && department.getId() != departmentService.getById(parentId).getId()){//防止在修改页面使用空id查询发生错误和选为本身的部门
			department.setParent(departmentService.getById(parentId));//设置所属上级部门
		}
		//更新到数据库
		if(parentId !=null){//
			departmentService.update(department);
		}else{
			departmentService.save(department);
		}
		return "toList";
	}

	// ---

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
