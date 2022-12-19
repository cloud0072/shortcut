package com.gravel.shortcut;

import com.gravel.shortcut.utils.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class AppTests {

//	@Qualifier("RandomGen")
//	@Resource
//	private GenNumService randomGen;
//
//	@Test
//	void contextLoads() {
//		List<Long> arr= new ArrayList<>();
//		for (int i = 0; i < 1000000; i++) {
//			arr.add(randomGen.genNum());
//		}
//		arr.sort(Long::compareTo);
//		System.out.println(arr.get(0));
//	}

    @Test
    void contextLoads() {

        boolean flag = Validator.checkUrl("https://www.hnlongyun.com/report/link/:home:HBMS:Used_cars:CRM:活动客户明细表_UCLY");

        System.out.println(flag);

    }

}
