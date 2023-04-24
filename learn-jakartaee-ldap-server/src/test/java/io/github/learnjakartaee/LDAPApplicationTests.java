package io.github.learnjakartaee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

@SpringBootTest
public class LDAPApplicationTests {

	@Autowired
	protected LdapTemplate ldapTemplate;
	
	@Test
	void testLdapUserSearch() {
		assertNotNull(ldapTemplate);
		List<String> names = ldapTemplate.search(query().where("objectclass").is("person"),
				new AttributesMapper<String>() {
					@Override
					public String mapFromAttributes(Attributes attributes) throws NamingException {
						return (String) attributes.get("cn").get();
					}
				});
		assertNotNull(names);
		assertEquals(3, names.size());
		assertEquals("Alice Aberdeen", names.get(0));
	}

}
