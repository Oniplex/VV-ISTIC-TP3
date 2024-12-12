package test;

import org.junit.jupiter.api.Test;

import static main.StringUtils.isBalanced;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    // Test 1 : Chaîne contenant uniquement des symboles de regroupement
    @Test
    void testOnlyGroupingSymbols() {
        assertTrue(isBalanced("()[]{}"), "Les symboles de regroupement doivent être équilibrés.");
        assertTrue(isBalanced("{[()]}"), "Les symboles imbriqués doivent être équilibrés.");
    }

    // Test 2 : Chaîne contenant des symboles de regroupement et d’autres caractères
    @Test
    void testGroupingAndOtherCharacters() {
        assertTrue(isBalanced("a(b)c"), "Les caractères non symboles ne doivent pas perturber l'équilibre.");
        assertTrue(isBalanced("{a[b(c)d]e}"), "Les symboles imbriqués avec des caractères doivent être équilibrés.");
    }

    // Test 3 : Chaîne ne contenant aucun symbole de regroupement
    @Test
    void testNoGroupingSymbols() {
        assertTrue(isBalanced(""), "La chaîne vide doit être équilibrée.");
        assertTrue(isBalanced("abcde"), "Une chaîne sans symboles de regroupement doit être équilibrée.");
    }

    // Test 4 : Symboles uniquement parenthèses
    @Test
    void testOnlyParentheses() {
        assertTrue(isBalanced("((()))"), "Les parenthèses doivent être équilibrées.");
    }

    // Test 5 : Symboles uniquement crochets
    @Test
    void testOnlyBrackets() {
        assertTrue(isBalanced("[[[]]]"), "Les crochets doivent être équilibrés.");
    }

    // Test 6 : Symboles uniquement accolades
    @Test
    void testOnlyBraces() {
        assertTrue(isBalanced("{{{}}}"), "Les accolades doivent être équilibrées.");
    }

    // Test 7 : Combinaison de différents types de symboles
    @Test
    void testMixedGroupingSymbols() {
        assertTrue(isBalanced("{[]()}"), "Les symboles mixtes doivent être équilibrés.");
    }

    // Test 8 : Symboles correctement appariés et imbriqués
    @Test
    void testProperlyNestedSymbols() {
        assertTrue(isBalanced("{[()]}"), "Les symboles imbriqués doivent être équilibrés.");
    }

    // Test 9 : Symboles correctement appariés mais non imbriqués
    @Test
    void testProperlyPairedButNotNested() {
        assertTrue(isBalanced("()[]{}"), "Les symboles correctement appariés mais non imbriqués doivent être équilibrés.");
    }

    // Test 10 : Symboles mal appariés ou mal imbriqués
    @Test
    void testImproperlyNestedSymbols() {
        assertFalse(isBalanced("([)]"), "Les symboles mal imbriqués ne doivent pas être équilibrés.");
        assertFalse(isBalanced("{(}{}"), "Les symboles mal imbriqués ne doivent pas être équilibrés.");
    }

    // Test 11 : Symboles d’ouverture sans correspondance de fermeture
    @Test
    void testUnmatchedOpeningSymbols() {
        assertFalse(isBalanced("((("), "Plusieurs symboles d'ouverture sans fermeture ne doivent pas être équilibrés.");
        assertFalse(isBalanced("{{"), "Plusieurs accolades d'ouverture sans fermeture ne doivent pas être équilibrées.");
    }

    // Test 12 : Symboles de fermeture sans correspondance d’ouverture
    @Test
    void testUnmatchedClosingSymbols() {
        assertFalse(isBalanced(")))"), "Plusieurs symboles de fermeture sans ouverture ne doivent pas être équilibrés.");
        assertFalse(isBalanced("}}"), "Plusieurs accolades de fermeture sans ouverture ne doivent pas être équilibrées.");
    }

    // Test 13 : Chaîne vide
    @Test
    void testEmptyString() {
        assertTrue(isBalanced(""), "La chaîne vide doit être équilibrée.");
    }

    // Test 14 : Chaîne de longueur paire
    @Test
    void testEvenLengthString() {
        assertTrue(isBalanced("()[]{}"), "Une chaîne de longueur paire correctement équilibrée doit retourner true.");
    }

    // Test 15 : Chaîne de longueur impaire
    @Test
    void testOddLengthString() {
        assertFalse(isBalanced("({})("), "Une chaîne de longueur impaire ne doit pas être équilibrée.");
    }

    // Test 16 : Chaîne null
    @Test
    void testNullString() {
        assertFalse(isBalanced(null), "Une chaîne null ne doit pas être équilibrée.");
    }

    // Test 17 : Chaîne contenant uniquement des symboles d’ouverture
    @Test
    void testOnlyOpeningSymbols() {
        assertFalse(isBalanced("((("), "Des symboles d'ouverture sans fermeture ne doivent pas être équilibrés.");
        assertFalse(isBalanced("[[["), "Des crochets d'ouverture sans fermeture ne doivent pas être équilibrés.");
    }

    // Test 18 : Chaîne contenant uniquement des symboles de fermeture
    @Test
    void testOnlyClosingSymbols() {
        assertFalse(isBalanced(")))"), "Des symboles de fermeture sans ouverture ne doivent pas être équilibrés.");
        assertFalse(isBalanced("]]]"), "Des crochets de fermeture sans ouverture ne doivent pas être équilibrés.");
    }

    @Test
    void testMixedNonMatchingPairs() {
        // open == '(' mais close != ')'
        assertFalse(isBalanced("(]"), "Paire '(' avec ']' ne doit pas être équilibrée.");

        // open == '{' mais close != '}'
        assertFalse(isBalanced("{)"), "Paire '{' avec ')' ne doit pas être équilibrée.");

        // open == '[' mais close != ']'
        assertFalse(isBalanced("[}"), "Paire '[' avec '}' ne doit pas être équilibrée.");
    }

    // Nouveau Cas de Test 20 : Paires avec Symboles de Fermeture Incorrects au Milieu
    @Test
    void testIncorrectClosingSymbolsInMiddle() {
        // Chaîne avec une fermeture incorrecte au milieu
        assertFalse(isBalanced("{[}(])}"), "Chaîne avec fermeture incorrecte au milieu ne doit pas être équilibrée.");
    }

    // Nouveau Cas de Test 21 : Symboles d'Ouverture Non Correspondants suivis de Fermeture
    @Test
    void testUnmatchedOpeningAndClosingSymbols() {
        // Plusieurs symboles d'ouverture sans fermetures correspondantes
        assertFalse(isBalanced("({["), "Chaîne avec multiples symboles d'ouverture sans fermetures ne doit pas être équilibrée.");

        // Symboles de fermeture sans symboles d'ouverture correspondants
        assertFalse(isBalanced(")]}"), "Chaîne avec multiples symboles de fermeture sans symboles d'ouverture ne doit pas être équilibrée.");
    }

    // Nouveau Cas de Test 22 : Symboles Mélangés avec Caractères Non Symboles
    @Test
    void testSymbolsWithNonMatchingCharacters() {
        // Symboles mélangés avec des caractères non symboles et des paires incorrectes
        assertFalse(isBalanced("{a[b(c]d)e}"), "Chaîne avec symboles mal imbriqués et caractères ne doit pas être équilibrée.");
    }
}