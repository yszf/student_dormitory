package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.DormRule;

public interface DormRuleDao {
	
	public DormRule queryDormRuleByID(Integer ruleID);
	
	public List<DormRule> queryDormRules();
	
}
