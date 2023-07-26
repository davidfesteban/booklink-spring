package es.misei.everi;

import es.misei.everi.application.OrganizationProcessor;
import es.misei.everi.repository.AuthRepository;
import es.misei.everi.repository.MaterialRepository;
import es.misei.everi.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EveriDBIntegrationTest {

    @Autowired
    private OrganizationProcessor organizationProcessor;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AuthRepository authRepository;

    @Test
    void happyPath() {
        /*User admin = new User();
        admin.setEmail("admin@test.com");
        User admin2 = new User();
        admin2.setEmail("admin2@test.com");
        User admin3 = new User();
        admin3.setEmail("admin3@test.com");

        OrganizationPayload organizationPayload = new OrganizationPayload();
        organizationPayload.setName("OneOrg");
        organizationPayload.setCode("1Org");
        OrganizationPayload organizationPayload2 = new OrganizationPayload();
        organizationPayload2.setName("TwoOrg");
        organizationPayload2.setCode("2Org");

        MaterialPayload material = new MaterialPayload();
        material.setQrCode("1Org_material");
        MaterialPayload material1 = new MaterialPayload();
        material1.setQrCode("1Org_material1");
        MaterialPayload material2 = new MaterialPayload();
        material2.setQrCode("2Org_material");

        authRepository.saveAndFlush(admin);
        authRepository.saveAndFlush(admin2);

        //Should NOT fail
        Assertions.assertThat(organizationProcessor.createOrganization(organizationPayload, admin.getEmail()).isResult()).isTrue();
        Assertions.assertThat(organizationProcessor.createOrganization(organizationPayload2, admin2.getEmail()).isResult()).isTrue();

        //Should fail
        organizationProcessor.createOrganization(organizationPayload, admin3.getEmail());

        //Should NOT fail
        Assertions.assertThat(organizationProcessor.addUser("1Org", admin.getEmail(), admin2.getEmail()).isResult()).isTrue();
        Assertions.assertThat(organizationProcessor.addUser("2Org", admin2.getEmail(), admin.getEmail()).isResult()).isTrue();

        //Should fail
        organizationProcessor.addUser("1Org", admin2.getEmail(), admin.getEmail());
        //organizationProcessor.addUser("2Org", admin2.getEmail(), admin3.getEmail());

        //Should NOT fail
        Assertions.assertThat(organizationProcessor.addMaterial("1Org", admin.getEmail(), material).isResult()).isTrue();
        Assertions.assertThat(organizationProcessor.addMaterial("1Org", admin2.getEmail(), material1).isResult()).isFalse();
        Assertions.assertThat(organizationProcessor.addMaterial("2Org", admin2.getEmail(), material2).isResult()).isTrue();

        //Should fail
        organizationProcessor.addMaterial("2Org", admin2.getEmail(), material2);
        organizationProcessor.addMaterial("3Org", admin2.getEmail(), material2);
        organizationProcessor.addMaterial("1Org", admin3.getEmail(), material);
        organizationProcessor.addMaterial("2Org", admin2.getEmail(), material1);

        Assertions.assertThat(authRepository.findByEmail(admin.getEmail())).isNotEmpty();
        Assertions.assertThat(authRepository.findByEmail(admin2.getEmail())).isNotEmpty();
        Assertions.assertThat(authRepository.findByEmail(admin3.getEmail())).isEmpty();
        Assertions.assertThat(organizationRepository.findByCode("1Org")).isNotEmpty();
        Assertions.assertThat(organizationRepository.findByCode("2Org")).isNotEmpty();
        Assertions.assertThat(materialRepository.findByQrCode(material.getQrCode())).isNotEmpty();
        Assertions.assertThat(materialRepository.findByQrCode(material1.getQrCode())).isNotEmpty();
        Assertions.assertThat(materialRepository.findByQrCode(material2.getQrCode())).isNotEmpty();

        //Should NOT fail
        organizationProcessor.removeMaterial("1Org", admin2.getEmail(), material1.getQrCode());
        organizationProcessor.removeUser("2Org", admin.getEmail());
        organizationProcessor.addMaterial("2Org", admin2.getEmail(), material1);

        //Should fail
        organizationProcessor.removeMaterial("2org", admin.getEmail(), material2.getQrCode());*/
    }

}
