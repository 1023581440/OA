package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.TopicService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService {

	@Deprecated
	public List<Topic> findByForum(Forum forum) {
		return getSession().createQuery(
				"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC")
				.setParameter(0, forum)
				.list();
	}
	
	@Override
	public void save(Topic topic) {
		//设置属性并保存
		topic.setType(Topic.TYPE_NORMAL);//默认为普通帖
		topic.setLastReply(null);
		topic.setReplyCount(0);
		topic.setLastUpdateTime(topic.getPostTime());
		
		getSession().save(topic);
		
		//维护相关的特殊属性
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount() + 1);//主题数量
		forum.setArticleCount(forum.getArticleCount() + 1);//文章数量（主题+回复）
		forum.setLastTopic(topic);//最后发表的主题
		
		getSession().update(forum);
	}
	
	@Deprecated
	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum) {
		List list = getSession().createQuery(
				"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC")
				.setParameter(0, forum)
				.setFirstResult((pageNum -1) * pageSize)
				.setMaxResults(pageSize)
				.list();
				
		Long count = (Long) getSession().createQuery(
				"SELECT count(*) FROM Topic t WHERE t.forum=?")
				.setParameter(0, forum)
				.uniqueResult();
				
		return new PageBean(pageNum, pageSize, count.intValue(), list);
	}

}
