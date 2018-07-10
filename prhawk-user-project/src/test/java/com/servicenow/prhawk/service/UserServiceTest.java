package com.servicenow.prhawk.service;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.servicenow.prhawk.model.*;

@RunWith(SpringRunner.class)
@RestClientTest({ UserService.class })
public class UserServiceTest {	
	@Autowired
	private UserService service;
	
	@Test
	public void getRepoPullTimesWhenResultExpected() {
		assertThat(this.service.getRepoPullTimes("https://api.github.com/repos/weixie08/prhawk/pulls?per_page=1")).isEqualTo(0);
	}
	
	@Test
	public void getUserReposDetailWhenResultExpected() {
		List<UserRepoDetail> result = this.service.getUserDetail("weixie08");
		assertThat(result.size()).isEqualTo(2);
		List<UserRepoDetail> mocks = new ArrayList<UserRepoDetail>();
		UserRepoDetail item1 = new UserRepoDetail("weixie08/prhawk", 0, "prhawk", "https://github.com/weixie08/prhawk");
		UserRepoDetail item2 = new UserRepoDetail("weixie08/Test", 0, "Test", "https://github.com/weixie08/Test");
		mocks.add(item1);
		mocks.add(item2);
		assertEquals(result, mocks);
	}
}
