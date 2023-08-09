package dev.misei;

import dev.misei.application.MailService;
import dev.misei.config.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestData.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BooklinkSystemTest extends BaseContainerizedTest {

    //@Autowired
    //private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestData testData;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MailService mailService;

    @Test
    void emailSend() {
        mailService.sendConfirmationMessage("david@misei.dev", "Esto es un test");
    }

    @Test
    void simpleTokenValid() throws Exception {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkIiwiZW1haWwiOiJkYXZpZEBtaXNlaS5kZXYiLCJpYXQiOjE2OTE2MTEwOTUsImV4cCI6MTY5MTYzOTg5NX0.xhm8lNaDTCQ3je4JpRirqdD12gbneT7MAWkpieYw6m6H1QTwonLXpGAfhtA3vTcsHgGrSEXwu5H8Imons5gumg");

        this.mvc.perform(get("/api/private/user/isValidToken")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)).andReturn();
    }

    @Test
    void simpleAdminAndUserAndAppointment() throws Exception {
        MvcResult joinUser = this.mvc.perform(post("/api/public/auth/join")
                .content(testData.joinDavid.getContentAsString(StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult joinWho = this.mvc.perform(post("/api/public/auth/join")
                .content(testData.joinWho.getContentAsString(StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult loginDavid = this.mvc.perform(get("/api/public/auth/login?email=david@misei.dev&password=GutPassword")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult loginWhoFail = this.mvc.perform(get("/api/public/auth/login?email=who@doctor.dev&password=CACA")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(loginWhoFail.getResponse().getStatus()).isNotEqualTo(200);

        MvcResult loginWho = this.mvc.perform(get("/api/public/auth/login?email=who@doctor.dev&password=caca")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult createCompanyByDavid = this.mvc.perform(post("/api/private/business/create")
                .content(testData.createCompany.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        MvcResult createCompanyByWho = this.mvc.perform(post("/api/private/business/create")
                .content(testData.createCompany2.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginWho))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        MvcResult createAppointmentByDavidToWho = this.mvc.perform(post("/api/private/appointment/create")
                .content(testData.appointmentCreate.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();



       /* var user = new ObjectMapper().readValue(userStatusFinal.getResponse().getContentAsString(StandardCharsets.UTF_8), UserPayload.class);
        var billing = new ObjectMapper().readValue(billingStatusFinal.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<BillingPayload>>(){});
        var material = new ObjectMapper().readValue(materialStatusFinal.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<MaterialPayload>>(){});

        assertThat(user.getEmail()).isEqualTo("david@test.com");
        assertThat(user.getAdminOrganizations()).isNotEmpty();
        assertThat(billing.get(0).getUserEmail()).isEqualTo("david@test.com");
        assertThat(material.get(0).getQrCode()).isEqualTo("ORG2_ABC123");*/
    }

    private HttpHeaders addTokenFromResult(MvcResult mvcResult) throws Exception {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return headers;
    }

}