package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonRefererTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonReferer.class);
        PersonReferer personReferer1 = new PersonReferer();
        personReferer1.setId("id1");
        PersonReferer personReferer2 = new PersonReferer();
        personReferer2.setId(personReferer1.getId());
        assertThat(personReferer1).isEqualTo(personReferer2);
        personReferer2.setId("id2");
        assertThat(personReferer1).isNotEqualTo(personReferer2);
        personReferer1.setId(null);
        assertThat(personReferer1).isNotEqualTo(personReferer2);
    }
}
