package com.thosepeople.service;

import java.util.List;

import com.thosepeople.po.JobInfo;
import com.thosepeople.vo.JobDetailInfo;
import com.thosepeople.vo.JobInfoProfile;

public interface JobService {

	boolean postJobInfo(JobInfo job);
	List<JobInfoProfile> loadJobInfo(String key);
	JobDetailInfo loadJobDetail(int jid);
}