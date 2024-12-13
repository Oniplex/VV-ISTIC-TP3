package test;

import main.SSLSocket;
import main.TLSSocketFactory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TLSSocketFactoryTestMocks {

    /**
     * Test du cas où les protocoles supportés et activés sont null.
     * Vérifie que setEnabledProtocols n'est pas appelé.
     */
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

    /**
     * Test typique où les protocoles supportés et activés sont fournis.
     * Vérifie que setEnabledProtocols est appelé avec les protocoles triés correctement.
     */
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
        assertTrue(Arrays.equals(expected, captor.getValue()), "Les protocoles ne sont pas triés correctement.");
    }

    /**
     * Méthode utilitaire pour mélanger les éléments d'un tableau.
     */
    private String[] shuffle(String[] in) {
        List<String> list = new ArrayList<>(Arrays.asList(in));
        Collections.shuffle(list);
        return list.toArray(new String[0]);
    }

    /**
     * Test supplémentaire : Vérifie que setEnabledProtocols est appelé uniquement avec les protocoles supportés et activés.
     */
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
        assertTrue(Arrays.equals(expected, captor.getValue()), "Les protocoles ne sont pas triés correctement.");
    }

    /**
     * Test supplémentaire : Vérifie le comportement lorsque getSupportedProtocols retourne un tableau vide.
     */
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
        assertTrue(Arrays.equals(expected, captor.getValue()), "Les protocoles ne sont pas réappliqués correctement.");
    }
}