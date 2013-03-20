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
public class ServiceAuthenticator implements Authenticator {

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

			principal = new User(credentials.getUsername());
			principal.setDisplayName(searchResult.getAttributes().get("cn").get().toString());
			principal.setEmail(searchResult.getAttributes().get("mail").get().toString());
			principal.setTelephoneNumber(searchResult.getAttributes().get("telephoneNumber").get().toString());

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

		// <module-option name="java.naming.factory.initial" value="com.sun.jndi.ldap.LdapCtxFactory"/>
		// <module-option name="java.naming.provider.url" value="ldap://10.200.238.209:389"/>
		// <module-option name="java.naming.security.authentication" value="simple"/>
		// <module-option name="bindDN" value="uid=webdesdr,ou=Servicos,ou=corp,dc=serpro,dc=gov,dc=br"/>
		// <module-option name="bindCredential" value="webdesdr"/>
		// <module-option name="baseCtxDN" value="dc=serpro,dc=gov,dc=br"/>
		// <module-option name="baseFilter" value="(uid={0})"/>
		// <module-option name="rolesCtxDN" value="dc=serpro,dc=gov,dc=br"/>
		// <module-option name="roleFilter" value="(member={1})"/>
		// <module-option name="roleAttributeID" value="cn"/>
		// <module-option name="throwValidateError" value="true"/>
		// <module-option name="searchScope" value="ONELEVEL_SCOPE"/>

		// if (this.conf.isServerSSL()) {
		// env.put(Context.SECURITY_PROTOCOL, "ssl");
		// }

		return new InitialLdapContext(env, null);
	}

	// private SearchResult searchByUsername(final String username) throws NamingException, IOException {
	// SearchResult result = null;
	//
	// for (final SearchResult searchResult : this.searchByFilter(username, false)) {
	// result = searchResult;
	// break;
	// }
	//
	// return result;
	// }
}
