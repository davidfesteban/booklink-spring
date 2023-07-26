package es.misei.everi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.misei.everi.config.jwt.JwtTokenProvider;
import es.misei.everi.domain.payload.BillingPayload;
import es.misei.everi.domain.payload.material.MaterialPayload;
import es.misei.everi.domain.payload.user.UserPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestData.class)
@AutoConfigureMockMvc
public class EveriSystemTest extends BaseContainerizedTest {

    //@Autowired
    //private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestData testData;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void happyPathForDavidAndJanUser() throws Exception {
        MvcResult joinDavid = this.mvc.perform(post("/api/auth/join")
                .content(testData.userDavid.getContentAsString(StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult joinJan = this.mvc.perform(post("/api/auth/join")
                .content(testData.userJan.getContentAsString(StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult loginDavid = this.mvc.perform(post("/api/auth/login")
                .content(testData.userDavid.getContentAsString(StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult loginJan = this.mvc.perform(post("/api/auth/login")
                .content(testData.userJan.getContentAsString(StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult createOrganization = this.mvc.perform(post("/api/everi/createOrganization")
                .content(testData.organizationMisei.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult addProject = this.mvc.perform(post("/api/everi/addProject?organizationCode=ORG2")
                .content(testData.projectIT.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        MvcResult addMaterial = this.mvc.perform(post("/api/everi/addMaterial?organizationCode=ORG2")
                .content(testData.materialComputer.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        MvcResult addUserToWaitlist = this.mvc.perform(get("/api/everi/addUserToWaitlist?organizationCode=ORG2")
                .headers(addTokenFromResult(loginJan))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult addUser = this.mvc.perform(get("/api/everi/addUser?organizationCode=ORG2&userToAddEmail=jan@test.com&asAdmin=false")
                .content(testData.userJan.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult addBilling = this.mvc.perform(post("/api/everi/addBilling?projectCode=ORG2_PRJ2")
                .content(testData.billingDavid.getContentAsString(StandardCharsets.UTF_8))
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult userStatusFinal = this.mvc.perform(get("/api/everi/findUserByEmail")
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult billingStatusFinal = this.mvc.perform(get("/api/everi/findAllBillingByProject?projectCode=ORG2_PRJ2")
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult materialStatusFinal = this.mvc.perform(get("/api/everi/findAllMaterialByOrganization?organizationCode=ORG2")
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult projectStatusFinal = this.mvc.perform(get("/api/everi/findAllProjectByOrganization?organizationCode=ORG2")
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult organizationStatusFinal = this.mvc.perform(get("/api/everi/findOrganizationByCode?code=ORG2")
                .headers(addTokenFromResult(loginDavid))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var user = new ObjectMapper().readValue(userStatusFinal.getResponse().getContentAsString(StandardCharsets.UTF_8), UserPayload.class);
        var billing = new ObjectMapper().readValue(billingStatusFinal.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<BillingPayload>>(){});
        var material = new ObjectMapper().readValue(materialStatusFinal.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<MaterialPayload>>(){});

        assertThat(user.getEmail()).isEqualTo("david@test.com");
        assertThat(user.getAdminOrganizations()).isNotEmpty();
        assertThat(billing.get(0).getUserEmail()).isEqualTo("david@test.com");
        assertThat(material.get(0).getQrCode()).isEqualTo("ORG2_ABC123");
    }

    private HttpHeaders addTokenFromResult(MvcResult mvcResult) throws Exception {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return headers;
    }

}