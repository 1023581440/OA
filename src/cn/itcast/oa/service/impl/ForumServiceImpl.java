/**
 * 
 */
package cn.itcast.oa.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;

/**
 * @author 佳亮
 *
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements ForumService {


	@Override
	public List<Forum> findAll() {
		return getSession().createQuery(
				"FROM Forum f ORDER BY f.position")
				.list();
	}
	
	@Override
	public void save(Forum forum) {
		super.save(forum);
		//设置position的值
		forum.setPosition(forum.getId().intValue());
	} 
	
	public void moveUp(Long id) {
		Forum forum = getById(id);
		Forum other = (Forum) getSession().createQuery(
					  "FROM Forum f WHERE f.position<? ORDER BY f.position DESC")
					  .setParameter(0, forum.getPosition())
					  .setFirstResult(0)
					  .setMaxResults(1)
					  .uniqueResult();//获取position上面的那个对象
		
		//最上面的不能上移
		if(other != null){
		//交换position的值
			int temp = forum.getPosition();
			forum.setPosition(other.getPosition());
			other.setPosition(temp);
		}
		
		//更新到数据库中（可以不写，因为对象现在是持久化的）
	}

	public void moveDown(Long id) {
		Forum forum = getById(id);
		Forum other = (Forum) getSession().createQuery(
					  "FROM Forum f WHERE f.position>? ORDER BY f.position ASC")
					  .setParameter(0, forum.getPosition())
					  .setFirstResult(0)
					  .setMaxResults(1)
					  .uniqueResult();//获取position上面的那个对象
		
		//最上面的不能上移
		if(other !=null){
			//交换position的值
			int temp = forum.getPosition();
			forum.setPosition(other.getPosition());
			other.setPosition(temp);
		}
		

		//更新到数据库中（可以不写，因为对象现在是持久化的）
	}
}
