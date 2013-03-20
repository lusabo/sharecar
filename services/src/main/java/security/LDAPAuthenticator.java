package security;

import java.security.Principal;
import java.util.Hashtable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpSession;

import br.gov.frameworkdemoiselle.security.AuthenticationException;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.Credentials;
import br.gov.frameworkdemoiselle.util.Beans;
import entity.User;

@SessionScoped
public class LDAPAuthenticator implements Authenticator {

	private static final long serialVersionUID = 1L;

	@Inject
	private Credentials credentials;

	private User principal;

	@Inject
	private LDAPConfig ldapConfig;

	@Override
	public void authenticate() throws AuthenticationException {

		try {
			LdapContext ldapContext = createContext();

			final SearchControls controls = new SearchControls();
			controls.setSearchScope(ldapConfig.getSearchScope());

			String filter = ldapConfig.getBaseFilter();
			filter = filter.replaceAll("\\{0\\}", credentials.getUsername());

			final NamingEnumeration<SearchResult> naming = ldapContext.search(ldapConfig.getBaseCtxDN(), filter,
					controls);
			ldapContext.close();

			SearchResult searchResult = null;

			while (naming.hasMoreElements()) {
				searchResult = naming.next();
				break;
			}

			final String username = searchResult.getNameInNamespace();

			ldapContext = createContext(username, credentials.getPassword());
			ldapContext.close();

			principal = createUser(searchResult);

		} catch (Exception cause) {
			cause.printStackTrace();
			throw new AuthenticationException(cause);
		}
	}

	@Override
	public void unAuthenticate() {
		principal = null;
		Beans.getReference(HttpSession.class).invalidate();
	}

	private User createUser(SearchResult searchResult) throws NamingException {
		User result = new User();

		principal.setName(searchResult.getAttributes().get("uid").get().toString());
		principal.setDisplayName(searchResult.getAttributes().get("cn").get().toString());
		principal.setEmail(searchResult.getAttributes().get("mail").get().toString());
		principal.setTelephoneNumber(searchResult.getAttributes().get("telephoneNumber").get().toString());

		return result;
	}

	@Override
	public Principal getUser() {
		return principal;
	}

	private LdapContext createContext() throws NamingException {
		return createContext(ldapConfig.getBindDN(), ldapConfig.getBindCredential());
	}

	private LdapContext createContext(String username, String password) throws NamingException {
		final Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapConfig.getProviderURL());
		env.put(Context.SECURITY_AUTHENTICATION, ldapConfig.getSecurityAuthentication());
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.REFERRAL, "follow");

		return new InitialLdapContext(env, null);
	}
}
