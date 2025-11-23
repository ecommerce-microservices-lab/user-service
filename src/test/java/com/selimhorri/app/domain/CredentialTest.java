package com.selimhorri.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CredentialTest {

    private Credential credential1;
    private Credential credential2;
    private Credential credential3;
    private User user;
    private Set<VerificationToken> verificationTokens;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        
        verificationTokens = new HashSet<>();
        VerificationToken token = new VerificationToken();
        token.setVerificationTokenId(1);
        verificationTokens.add(token);

        credential1 = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .user(user)
                .verificationTokens(verificationTokens)
                .build();

        credential2 = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .user(user)
                .verificationTokens(verificationTokens)
                .build();

        credential3 = Credential.builder()
                .credentialId(2)
                .username("user2")
                .password("password2")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_ADMIN)
                .isEnabled(false)
                .isAccountNonExpired(false)
                .isAccountNonLocked(false)
                .isCredentialsNonExpired(false)
                .user(new User()) // Different user
                .verificationTokens(new HashSet<>()) // Empty set
                .build();
    }

    @Test
    void testEquals() {
        // Test equality with same values
        assertEquals(credential1, credential2);
        
        // Test inequality with different values
        assertNotEquals(credential1, credential3);
        
        // Test with null
        assertNotEquals(null, credential1);
        
        // Test with different class
        assertNotEquals(credential1, new Object());
    }

    @Test
    void testHashCode() {
        // Equal objects must have equal hash codes
        assertEquals(credential1.hashCode(), credential2.hashCode());
        
        // Unequal objects should ideally have different hash codes
        assertNotEquals(credential1.hashCode(), credential3.hashCode());
    }

    @Test
    void testToString() {
        String toString = credential1.toString();
        
        // Verify some key fields are included
        assertTrue(toString.contains("credentialId=1"));
        assertTrue(toString.contains("username=user1"));
        assertTrue(toString.contains("roleBasedAuthority=ROLE_USER"));
        
    }

    @Test
    void testCanEqual() {
        // Should be able to equal another Credential
        assertTrue(credential1.canEqual(credential2));
        
        // Should not be able to equal non-Credential objects
        assertFalse(credential1.canEqual(new Object()));
        assertFalse(credential1.canEqual(null));
    }

    @Test
    void testGetVerificationTokens() {
        // Test getter returns the same set we provided
        assertEquals(verificationTokens, credential1.getVerificationTokens());
        
        // Verify the set contains our token
        assertEquals(1, credential1.getVerificationTokens().size());
        assertEquals(1, credential1.getVerificationTokens().iterator().next().getVerificationTokenId());
        
        // Test with empty set
        assertEquals(0, credential3.getVerificationTokens().size());
    }

    @Test
    void testExcludedFields() {
        // Verify @EqualsAndHashCode.Exclude works for user field
        Credential credential4 = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .user(new User()) // Different user but should not affect equals
                .verificationTokens(verificationTokens)
                .build();
        
        assertEquals(credential1, credential4);
    }

    @Test
    void testNoArgsConstructor() {
        Credential credential = new Credential();
        assertNotNull(credential);
        assertNull(credential.getCredentialId());
        assertNull(credential.getUsername());
        assertNull(credential.getPassword());
        assertNull(credential.getRoleBasedAuthority());
        assertNull(credential.getIsEnabled());
        assertNull(credential.getIsAccountNonExpired());
        assertNull(credential.getIsAccountNonLocked());
        assertNull(credential.getIsCredentialsNonExpired());
        assertNull(credential.getUser());
        assertNull(credential.getVerificationTokens());
    }

    @Test
    void testAllArgsConstructor() {
        User testUser = new User();
        testUser.setUserId(100);
        
        Set<VerificationToken> testTokens = new HashSet<>();
        VerificationToken token1 = new VerificationToken();
        token1.setVerificationTokenId(10);
        testTokens.add(token1);
        
        Credential credential = new Credential(
            5,
            "testuser",
            "testpass",
            RoleBasedAuthority.ROLE_ADMIN,
            false,
            false,
            false,
            false,
            testUser,
            testTokens
        );
        
        assertEquals(5, credential.getCredentialId());
        assertEquals("testuser", credential.getUsername());
        assertEquals("testpass", credential.getPassword());
        assertEquals(RoleBasedAuthority.ROLE_ADMIN, credential.getRoleBasedAuthority());
        assertFalse(credential.getIsEnabled());
        assertFalse(credential.getIsAccountNonExpired());
        assertFalse(credential.getIsAccountNonLocked());
        assertFalse(credential.getIsCredentialsNonExpired());
        assertEquals(testUser, credential.getUser());
        assertEquals(testTokens, credential.getVerificationTokens());
    }

    @Test
    void testGettersAndSetters() {
        Credential credential = new Credential();
        
        credential.setCredentialId(99);
        credential.setUsername("newuser");
        credential.setPassword("newpass");
        credential.setRoleBasedAuthority(RoleBasedAuthority.ROLE_USER);
        credential.setIsEnabled(false);
        credential.setIsAccountNonExpired(false);
        credential.setIsAccountNonLocked(false);
        credential.setIsCredentialsNonExpired(false);
        
        User newUser = new User();
        newUser.setUserId(200);
        credential.setUser(newUser);
        
        Set<VerificationToken> newTokens = new HashSet<>();
        VerificationToken newToken = new VerificationToken();
        newToken.setVerificationTokenId(20);
        newTokens.add(newToken);
        credential.setVerificationTokens(newTokens);
        
        assertEquals(99, credential.getCredentialId());
        assertEquals("newuser", credential.getUsername());
        assertEquals("newpass", credential.getPassword());
        assertEquals(RoleBasedAuthority.ROLE_USER, credential.getRoleBasedAuthority());
        assertFalse(credential.getIsEnabled());
        assertFalse(credential.getIsAccountNonExpired());
        assertFalse(credential.getIsAccountNonLocked());
        assertFalse(credential.getIsCredentialsNonExpired());
        assertEquals(newUser, credential.getUser());
        assertEquals(newTokens, credential.getVerificationTokens());
    }

    @Test
    void testToString_AllFields() {
        String toString = credential1.toString();
        
        assertTrue(toString.contains("credentialId=1"));
        assertTrue(toString.contains("username=user1"));
        assertTrue(toString.contains("password=password1"));
        assertTrue(toString.contains("roleBasedAuthority=ROLE_USER"));
        assertTrue(toString.contains("isEnabled=true"));
        assertTrue(toString.contains("isAccountNonExpired=true"));
        assertTrue(toString.contains("isAccountNonLocked=true"));
        assertTrue(toString.contains("isCredentialsNonExpired=true"));
    }

    @Test
    void testEquals_DifferentCredentialId() {
        Credential differentId = Credential.builder()
                .credentialId(999)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        
        assertNotEquals(credential1, differentId);
    }

    @Test
    void testEquals_DifferentUsername() {
        Credential differentUsername = Credential.builder()
                .credentialId(1)
                .username("different")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        
        assertNotEquals(credential1, differentUsername);
    }

    @Test
    void testEquals_DifferentPassword() {
        Credential differentPassword = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("different")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        
        assertNotEquals(credential1, differentPassword);
    }

    @Test
    void testEquals_DifferentRoleBasedAuthority() {
        Credential differentRole = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_ADMIN)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        
        assertNotEquals(credential1, differentRole);
    }

    @Test
    void testEquals_DifferentIsEnabled() {
        Credential differentEnabled = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(false)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        
        assertNotEquals(credential1, differentEnabled);
    }

    @Test
    void testEquals_DifferentIsAccountNonExpired() {
        Credential differentAccountExpired = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(false)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        
        assertNotEquals(credential1, differentAccountExpired);
    }

    @Test
    void testEquals_DifferentIsAccountNonLocked() {
        Credential differentAccountLocked = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(false)
                .isCredentialsNonExpired(true)
                .build();
        
        assertNotEquals(credential1, differentAccountLocked);
    }

    @Test
    void testEquals_DifferentIsCredentialsNonExpired() {
        Credential differentCredentialsExpired = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(false)
                .build();
        
        assertNotEquals(credential1, differentCredentialsExpired);
    }

    @Test
    void testEquals_VerificationTokensExcluded() {
        Set<VerificationToken> differentTokens = new HashSet<>();
        VerificationToken token2 = new VerificationToken();
        token2.setVerificationTokenId(999);
        differentTokens.add(token2);
        
        Credential credential4 = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .user(user)
                .verificationTokens(differentTokens) // Different tokens but should not affect equals
                .build();
        
        assertEquals(credential1, credential4);
    }

    @Test
    void testEquals_Reflexivity() {
        assertEquals(credential1, credential1);
    }

    @Test
    void testEquals_Symmetry() {
        assertEquals(credential1, credential2);
        assertEquals(credential2, credential1);
    }

    @Test
    void testEquals_Transitivity() {
        Credential credential4 = Credential.builder()
                .credentialId(1)
                .username("user1")
                .password("password1")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .user(new User())
                .verificationTokens(new HashSet<>())
                .build();
        
        assertEquals(credential1, credential2);
        assertEquals(credential2, credential4);
        assertEquals(credential1, credential4);
    }

    @Test
    void testEquals_Consistency() {
        assertEquals(credential1, credential2);
        assertEquals(credential1, credential2);
        assertNotEquals(credential1, credential3);
        assertNotEquals(credential1, credential3);
    }

    @Test
    void testEquals_NullFields() {
        Credential nullFields = Credential.builder()
                .credentialId(null)
                .username(null)
                .password(null)
                .roleBasedAuthority(null)
                .isEnabled(null)
                .isAccountNonExpired(null)
                .isAccountNonLocked(null)
                .isCredentialsNonExpired(null)
                .build();
        
        Credential alsoNullFields = Credential.builder()
                .credentialId(null)
                .username(null)
                .password(null)
                .roleBasedAuthority(null)
                .isEnabled(null)
                .isAccountNonExpired(null)
                .isAccountNonLocked(null)
                .isCredentialsNonExpired(null)
                .build();
        
        assertEquals(nullFields, alsoNullFields);
        assertNotEquals(credential1, nullFields);
    }

    @Test
    void testHashCode_Consistency() {
        int hashCode1 = credential1.hashCode();
        int hashCode2 = credential1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testHashCode_EqualObjects() {
        assertEquals(credential1.hashCode(), credential2.hashCode());
    }

    @Test
    void testRoleBasedAuthority_ROLE_USER() {
        Credential userCredential = Credential.builder()
                .roleBasedAuthority(RoleBasedAuthority.ROLE_USER)
                .build();
        
        assertEquals(RoleBasedAuthority.ROLE_USER, userCredential.getRoleBasedAuthority());
    }

    @Test
    void testRoleBasedAuthority_ROLE_ADMIN() {
        Credential adminCredential = Credential.builder()
                .roleBasedAuthority(RoleBasedAuthority.ROLE_ADMIN)
                .build();
        
        assertEquals(RoleBasedAuthority.ROLE_ADMIN, adminCredential.getRoleBasedAuthority());
    }

    @Test
    void testBuilder_AllFields() {
        Credential built = Credential.builder()
                .credentialId(100)
                .username("builderuser")
                .password("builderpass")
                .roleBasedAuthority(RoleBasedAuthority.ROLE_ADMIN)
                .isEnabled(false)
                .isAccountNonExpired(false)
                .isAccountNonLocked(false)
                .isCredentialsNonExpired(false)
                .user(user)
                .verificationTokens(verificationTokens)
                .build();
        
        assertEquals(100, built.getCredentialId());
        assertEquals("builderuser", built.getUsername());
        assertEquals("builderpass", built.getPassword());
        assertEquals(RoleBasedAuthority.ROLE_ADMIN, built.getRoleBasedAuthority());
        assertFalse(built.getIsEnabled());
        assertFalse(built.getIsAccountNonExpired());
        assertFalse(built.getIsAccountNonLocked());
        assertFalse(built.getIsCredentialsNonExpired());
        assertEquals(user, built.getUser());
        assertEquals(verificationTokens, built.getVerificationTokens());
    }

    @Test
    void testGetUser() {
        assertEquals(user, credential1.getUser());
        assertEquals(1, credential1.getUser().getUserId());
    }

    @Test
    void testSetUser() {
        User newUser = new User();
        newUser.setUserId(300);
        credential1.setUser(newUser);
        assertEquals(newUser, credential1.getUser());
        assertEquals(300, credential1.getUser().getUserId());
    }

    @Test
    void testSetVerificationTokens() {
        Set<VerificationToken> newTokens = new HashSet<>();
        VerificationToken token3 = new VerificationToken();
        token3.setVerificationTokenId(30);
        newTokens.add(token3);
        
        credential1.setVerificationTokens(newTokens);
        assertEquals(newTokens, credential1.getVerificationTokens());
        assertEquals(1, credential1.getVerificationTokens().size());
    }
}