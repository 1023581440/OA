package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

public interface ReplyService extends DaoSupport<Reply>{

	/**
	 * 查询指定主题中的所有回复，排序：按发表时间升序排列。
	 * @param topic
	 * @return
	 */
	List<Reply> findByTopic(Topic topic);



//	/**
//	 * 查询分页信息
//	 * @param pageNum
//	 * @param pageSize
//	 * @param topic
//	 * @return
//	 */
//	PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic);

}
