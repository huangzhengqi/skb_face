/*
package com.fzy.admin.fp;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.controller.AppletMemberController;
import com.fzy.admin.fp.member.member.dto.WxMaSessionDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {LysjPayMasterApplication.class}
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Slf4j
public class AppletMemberControllerTest {


    @Autowired
    private WebApplicationContext context;

//    @Autowired
//    private AppletMemberController appletMemberController;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void testGetWxMaSessionInfo() throws Exception {
//
//        Resp<WxMaSessionDTO> resp = appletMemberController.getWxMaSessionInfo("wx473991bd67c67a48", "023hdxO20NmbcK1DBKO20hQRO20hdxO8");
//
//        System.out.println(resp);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/wx_ma_session")
                        .param("appId", "wx473991bd67c67a48")
                        .param("code", "023hdxO20NmbcK1DBKO20hQRO20hdxO8")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        Assert.assertEquals("nice", content);
    }
}
*/
