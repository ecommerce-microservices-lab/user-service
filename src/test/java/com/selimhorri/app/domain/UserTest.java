package com.selimhorri.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user1;
    private User user2;
    private User user3;
    private Set<Address> addresses;
    private Credential credential;

    @BeforeEach
    void setUp() {
        addresses = new HashSet<>();
        Address address = new Address();
        address.setAddressId(1);
        addresses.add(address);

        credential = new Credential();
        credential.setCredentialId(1);

        user1 = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .addresses(addresses)
                .credential(credential)
                .build();

        user2 = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .addresses(new HashSet<>(addresses)) // Different set instance
                .credential(credential)
                .build();

        user3 = User.builder()
                .userId(2)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phone("0987654321")
                .addresses(new HashSet<>()) // Empty set
                .credential(new Credential()) // Different credential
                .build();
    }

    @Test
    void testEquals() {
        // Test equality with same values (addresses and credential excluded)
        assertEquals(user1, user2);
        
        // Test inequality with different values
        assertNotEquals(user1, user3);
        
        // Test with null
        assertNotEquals(null, user1);
        
        // Test with different class
        assertNotEquals(user1, new Object());
    }

    @Test
    void testHashCode() {
        // Equal objects must have equal hash codes (addresses and credential excluded)
        assertEquals(user1.hashCode(), user2.hashCode());
        
        // Unequal objects should have different hash codes
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testSetAddresses() {
        Set<Address> newAddresses = new HashSet<>();
        Address newAddress = new Address();
        newAddress.setAddressId(2);
        newAddresses.add(newAddress);

        user1.setAddresses(newAddresses);
        
        // Verify the addresses were set correctly
        assertEquals(1, user1.getAddresses().size());

    }

    @Test
    void testCanEqual() {
        // Should be able to equal another User
        assertTrue(user1.canEqual(user2));
        
        // Should not be able to equal non-User objects
        assertFalse(user1.canEqual(new Object()));
        assertFalse(user1.canEqual(null));
    }

    @Test
    void testExcludedFieldsInEqualsAndHashCode() {
        // Create a user with different addresses but same other fields
        Set<Address> differentAddresses = new HashSet<>();
        Address diffAddress = new Address();
        diffAddress.setAddressId(3);
        differentAddresses.add(diffAddress);
        
        User user4 = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .addresses(differentAddresses) // Different addresses
                .credential(new Credential()) // Different credential
                .build();
        
        // Should still be equal because addresses and credential are excluded
        assertEquals(user1, user4);
        assertEquals(user1.hashCode(), user4.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
        assertNull(user.getUserId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getImageUrl());
        assertNull(user.getEmail());
        assertNull(user.getPhone());
        assertNull(user.getAddresses());
        assertNull(user.getCredential());
    }

    @Test
    void testAllArgsConstructor() {
        Set<Address> testAddresses = new HashSet<>();
        Address testAddress = new Address();
        testAddress.setAddressId(100);
        testAddresses.add(testAddress);
        
        Credential testCredential = new Credential();
        testCredential.setCredentialId(200);
        
        User user = new User(
            50,
            "Test",
            "User",
            "http://example.com/image.jpg",
            "test@example.com",
            "5551234567",
            testAddresses,
            testCredential
        );
        
        assertEquals(50, user.getUserId());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("http://example.com/image.jpg", user.getImageUrl());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("5551234567", user.getPhone());
        assertEquals(testAddresses, user.getAddresses());
        assertEquals(testCredential, user.getCredential());
    }

    @Test
    void testGettersAndSetters() {
        User user = new User();
        
        user.setUserId(99);
        user.setFirstName("New");
        user.setLastName("Name");
        user.setImageUrl("http://newimage.com/pic.jpg");
        user.setEmail("new@example.com");
        user.setPhone("9998887777");
        
        Set<Address> newAddresses = new HashSet<>();
        Address newAddress = new Address();
        newAddress.setAddressId(200);
        newAddresses.add(newAddress);
        user.setAddresses(newAddresses);
        
        Credential newCredential = new Credential();
        newCredential.setCredentialId(300);
        user.setCredential(newCredential);
        
        assertEquals(99, user.getUserId());
        assertEquals("New", user.getFirstName());
        assertEquals("Name", user.getLastName());
        assertEquals("http://newimage.com/pic.jpg", user.getImageUrl());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("9998887777", user.getPhone());
        assertEquals(newAddresses, user.getAddresses());
        assertEquals(newCredential, user.getCredential());
    }

    @Test
    void testToString() {
        String toString = user1.toString();
        
        assertTrue(toString.contains("userId=1"));
        assertTrue(toString.contains("firstName=John"));
        assertTrue(toString.contains("lastName=Doe"));
        assertTrue(toString.contains("email=john.doe@example.com"));
        assertTrue(toString.contains("phone=1234567890"));
    }

    @Test
    void testToString_WithImageUrl() {
        user1.setImageUrl("http://example.com/image.jpg");
        String toString = user1.toString();
        assertTrue(toString.contains("imageUrl=http://example.com/image.jpg"));
    }

    @Test
    void testEquals_DifferentUserId() {
        User differentId = User.builder()
                .userId(999)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
        
        assertNotEquals(user1, differentId);
    }

    @Test
    void testEquals_DifferentFirstName() {
        User differentFirstName = User.builder()
                .userId(1)
                .firstName("Jane")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
        
        assertNotEquals(user1, differentFirstName);
    }

    @Test
    void testEquals_DifferentLastName() {
        User differentLastName = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Smith")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
        
        assertNotEquals(user1, differentLastName);
    }

    @Test
    void testEquals_DifferentEmail() {
        User differentEmail = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("different@example.com")
                .phone("1234567890")
                .build();
        
        assertNotEquals(user1, differentEmail);
    }

    @Test
    void testEquals_DifferentPhone() {
        User differentPhone = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("9999999999")
                .build();
        
        assertNotEquals(user1, differentPhone);
    }

    @Test
    void testEquals_DifferentImageUrl() {
        User differentImageUrl = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .imageUrl("http://different.com/image.jpg")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
        
        assertNotEquals(user1, differentImageUrl);
    }

    @Test
    void testEquals_AddressesExcluded() {
        Set<Address> completelyDifferentAddresses = new HashSet<>();
        Address diffAddress = new Address();
        diffAddress.setAddressId(999);
        completelyDifferentAddresses.add(diffAddress);
        
        User user4 = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .addresses(completelyDifferentAddresses) // Different addresses but should not affect equals
                .credential(credential)
                .build();
        
        assertEquals(user1, user4);
    }

    @Test
    void testEquals_CredentialExcluded() {
        Credential completelyDifferentCredential = new Credential();
        completelyDifferentCredential.setCredentialId(999);
        completelyDifferentCredential.setUsername("different");
        
        User user4 = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .addresses(addresses)
                .credential(completelyDifferentCredential) // Different credential but should not affect equals
                .build();
        
        assertEquals(user1, user4);
    }

    @Test
    void testEquals_Reflexivity() {
        assertEquals(user1, user1);
    }

    @Test
    void testEquals_Symmetry() {
        assertEquals(user1, user2);
        assertEquals(user2, user1);
    }

    @Test
    void testEquals_Transitivity() {
        User user4 = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .addresses(new HashSet<>())
                .credential(new Credential())
                .build();
        
        assertEquals(user1, user2);
        assertEquals(user2, user4);
        assertEquals(user1, user4);
    }

    @Test
    void testEquals_Consistency() {
        assertEquals(user1, user2);
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user1, user3);
    }

    @Test
    void testEquals_NullFields() {
        User nullFields = User.builder()
                .userId(null)
                .firstName(null)
                .lastName(null)
                .imageUrl(null)
                .email(null)
                .phone(null)
                .build();
        
        User alsoNullFields = User.builder()
                .userId(null)
                .firstName(null)
                .lastName(null)
                .imageUrl(null)
                .email(null)
                .phone(null)
                .build();
        
        assertEquals(nullFields, alsoNullFields);
        assertNotEquals(user1, nullFields);
    }

    @Test
    void testHashCode_Consistency() {
        int hashCode1 = user1.hashCode();
        int hashCode2 = user1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testHashCode_EqualObjects() {
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testBuilder_AllFields() {
        User built = User.builder()
                .userId(200)
                .firstName("Builder")
                .lastName("Test")
                .imageUrl("http://builder.com/image.jpg")
                .email("builder@example.com")
                .phone("1112223333")
                .addresses(addresses)
                .credential(credential)
                .build();
        
        assertEquals(200, built.getUserId());
        assertEquals("Builder", built.getFirstName());
        assertEquals("Test", built.getLastName());
        assertEquals("http://builder.com/image.jpg", built.getImageUrl());
        assertEquals("builder@example.com", built.getEmail());
        assertEquals("1112223333", built.getPhone());
        assertEquals(addresses, built.getAddresses());
        assertEquals(credential, built.getCredential());
    }

    @Test
    void testGetAddresses() {
        assertEquals(addresses, user1.getAddresses());
        assertEquals(1, user1.getAddresses().size());
    }

    @Test
    void testGetCredential() {
        assertEquals(credential, user1.getCredential());
        assertEquals(1, user1.getCredential().getCredentialId());
    }

    @Test
    void testSetCredential() {
        Credential newCredential = new Credential();
        newCredential.setCredentialId(400);
        user1.setCredential(newCredential);
        assertEquals(newCredential, user1.getCredential());
        assertEquals(400, user1.getCredential().getCredentialId());
    }

    @Test
    void testGetFirstName() {
        assertEquals("John", user1.getFirstName());
    }

    @Test
    void testGetLastName() {
        assertEquals("Doe", user1.getLastName());
    }

    @Test
    void testGetEmail() {
        assertEquals("john.doe@example.com", user1.getEmail());
    }

    @Test
    void testGetPhone() {
        assertEquals("1234567890", user1.getPhone());
    }

    @Test
    void testGetImageUrl() {
        user1.setImageUrl("http://test.com/image.jpg");
        assertEquals("http://test.com/image.jpg", user1.getImageUrl());
    }

    @Test
    void testSetFirstName() {
        user1.setFirstName("Updated");
        assertEquals("Updated", user1.getFirstName());
    }

    @Test
    void testSetLastName() {
        user1.setLastName("Name");
        assertEquals("Name", user1.getLastName());
    }

    @Test
    void testSetEmail() {
        user1.setEmail("updated@example.com");
        assertEquals("updated@example.com", user1.getEmail());
    }

    @Test
    void testSetPhone() {
        user1.setPhone("0000000000");
        assertEquals("0000000000", user1.getPhone());
    }

    @Test
    void testSetImageUrl() {
        user1.setImageUrl("http://newimage.com/pic.jpg");
        assertEquals("http://newimage.com/pic.jpg", user1.getImageUrl());
    }

    @Test
    void testSetUserId() {
        user1.setUserId(500);
        assertEquals(500, user1.getUserId());
    }

    @Test
    void testGetUserId() {
        assertEquals(1, user1.getUserId());
    }
}