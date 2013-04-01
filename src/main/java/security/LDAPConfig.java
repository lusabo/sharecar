package security;

import java.lang.reflect.Field;

import javax.naming.directory.SearchControls;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(prefix = "frameworkdemoiselle.ldap.")
public class LDAPConfig {

	@Name("provider.url")
	private String providerURL;

	@Name("security.authentication")
	private String securityAuthentication;

	@Name("bind.dn")
	private String bindDN;

	@Name("bind.credential")
	private String bindCredential;

	@Name("base.ctx.dn")
	private String baseCtxDN;

	@Name("base.filter")
	private String baseFilter;

	@Name("search.scope")
	private String searchScope = "SUBTREE_SCOPE";

	public String getProviderURL() {
		return providerURL;
	}

	public String getSecurityAuthentication() {
		return securityAuthentication;
	}

	public String getBindDN() {
		return bindDN;
	}

	public String getBindCredential() {
		return bindCredential;
	}

	public String getBaseCtxDN() {
		return baseCtxDN;
	}

	public String getBaseFilter() {
		return baseFilter;
	}

	public Integer getSearchScope() {
		Integer result;

		try {
			Field field = SearchControls.class.getField(searchScope);
			field.setAccessible(true);
			result = field.getInt(null);

		} catch (Exception cause) {
			throw new IllegalArgumentException(
					"O valor "
							+ searchScope
							+ " para a configuração frameworkdemoiselle.ldap.search.scope não é válido. Verifique as opções na interface javax.naming.directory.SearchControls.",
					cause);
		}

		return result;
	}
}
