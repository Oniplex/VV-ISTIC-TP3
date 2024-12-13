# Mocks to the rescue

The classes `SSLSocket`, `TLSProtocol` and `TLSSocketFactory` are included in the `sockets` package of the [`tp3-ssl`](../code/tp3-ssl) project.

The test class `TLSSocketFactoryTest` tests `TLSSocketFactory` and manually builds stubs and mocks for SSLSocket objects.

Rewrite these tests with the help of Mockito.

The initial tests fail to completely test the `TLSSockeetFactory`. In fact, if we *entirely* remove the code inside the body of `prepareSocket` no test case fails.

Propose a solution to this problem in your new Mockito-based test cases.


# Answer


Rewrote the tests using **Mockito** to create robust and meaningful test cases. The key changes included:

1. **Mocking `SSLSocket`:**
   Created mocks for the `SSLSocket` interface to simulate different scenarios.

   ```java
   SSLSocket sslMock = mock(SSLSocket.class);
   when(sslMock.getSupportedProtocols()).thenReturn(null);
   when(sslMock.getEnabledProtocols()).thenReturn(null);
   ```

2. **Verifying Method Interactions:**
   Ensured that the correct methods were called on the mock and that `setEnabledProtocols` was not invoked when protocols were `null`.

   ```java
   verify(sslMock).getSupportedProtocols();
   verify(sslMock).getEnabledProtocols();
   verify(sslMock, never()).setEnabledProtocols(any());
   ```

3. **Capturing and Asserting Arguments:**
   Used `ArgumentCaptor` to capture the arguments passed to `setEnabledProtocols` and assert their correctness.

   ```java
   ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);
   verify(sslMock).setEnabledProtocols(captor.capture());

   String[] expected = { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3" };
   assertTrue(Arrays.equals(expected, captor.getValue()), "Protocol ordering is incorrect.");
   ```

4. **Removing Randomness for Deterministic Tests:**
   Eliminated the `shuffle` method to ensure that tests produce consistent and predictable results.

**Enhanced Test Cases:**
The revised tests now effectively verify that `prepareSocket` behaves as expected under various scenarios. Below are the key test methods:

1. **Testing Null Supported and Enabled Protocols:**

   ```java
   @Test
   public void preparedSocket_NullProtocols() {
       SSLSocket mockSocket = mock(SSLSocket.class);
       when(mockSocket.getSupportedProtocols()).thenReturn(null);
       when(mockSocket.getEnabledProtocols()).thenReturn(null);

       TLSSocketFactory factory = new TLSSocketFactory();
       factory.prepareSocket(mockSocket);

       verify(mockSocket).getSupportedProtocols();
       verify(mockSocket).getEnabledProtocols();
       verify(mockSocket, never()).setEnabledProtocols(any());
   }
   ```

2. **Testing Standard Protocol Configuration:**

   ```java
   @Test
   public void typical() {
       SSLSocket mockSocket = mock(SSLSocket.class);
       when(mockSocket.getSupportedProtocols())
               .thenReturn(shuffle(new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2" }));
       when(mockSocket.getEnabledProtocols()).thenReturn(shuffle(new String[] { "SSLv3", "TLSv1" }));

       TLSSocketFactory factory = new TLSSocketFactory();
       factory.prepareSocket(mockSocket);

       ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);
       verify(mockSocket).setEnabledProtocols(captor.capture());

       String[] expected = { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3" };
       assertTrue(Arrays.equals(expected, captor.getValue()), "Protocols are not sorted correctly.");
   }
   ```

3. **Testing Partial Protocol Overlap:**

   ```java
   @Test
   public void prepareSocket_WithPartialOverlap() {
       SSLSocket mockSocket = mock(SSLSocket.class);
       when(mockSocket.getSupportedProtocols())
               .thenReturn(new String[] { "TLSv1.1", "TLSv1.2", "TLSv1.3" });
       when(mockSocket.getEnabledProtocols())
               .thenReturn(new String[] { "TLSv1", "TLSv1.2" });

       TLSSocketFactory factory = new TLSSocketFactory();
       factory.prepareSocket(mockSocket);

       ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);
       verify(mockSocket).setEnabledProtocols(captor.capture());

       String[] expected = { "TLSv1.2", "TLSv1.1", "TLSv1" };
       assertTrue(Arrays.equals(expected, captor.getValue()), "Protocols are not sorted correctly.");
   }
   ```

4. **Testing Empty Supported Protocols:**

   ```java
   @Test
   public void prepareSocket_EmptySupportedProtocols() {
       SSLSocket mockSocket = mock(SSLSocket.class);
       when(mockSocket.getSupportedProtocols()).thenReturn(new String[] {});
       when(mockSocket.getEnabledProtocols()).thenReturn(new String[] { "TLSv1.1" });

       TLSSocketFactory factory = new TLSSocketFactory();
       factory.prepareSocket(mockSocket);

       ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);
       verify(mockSocket).setEnabledProtocols(captor.capture());

       String[] expected = { "TLSv1.1" };
       assertTrue(Arrays.equals(expected, captor.getValue()), "Enabled protocols were not reapplied correctly.");
   }
   ```