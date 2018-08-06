package cn.itcast.oa.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 主题
 * @author 佳亮
 *
 */
public class Topic extends Article{
	
	/**
	 * 普通帖
	 */
	public static final int TYPE_NORMAL = 0;
	/**
	 * 精华帖
	 */
	public static final int TYPE_BEST = 1;
	
	/**
	 * 置顶帖
	 */
	public static final int TYPE_TOP = 2;	
	
	private Forum forum;//对应的板块
	private Set<Reply> replies = new HashSet<Reply>();//主题的所有回复
	private int type;//主题类型，分为置顶帖，精华帖和普通帖
	private int replyCount;//回复数量
	private Reply lastReply;//最后的回复
	private Date lastUpdateTime;//最后发表更新的时间
	
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	public Set<Reply> getReplies() {
		return replies;
	}
	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public Reply getLastReply() {
		return lastReply;
	}
	public void setLastReply(Reply lastReply) {
		this.lastReply = lastReply;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
