
        import static org.junit.jupiter.api.Assertions.*;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;


public class Teste {


    FileManager fm;
    FileManager fm2;

    @BeforeEach
    public void init(){
        fm = new FileManager();
        fm2 = new FileManager(20);
    }


    @Test//Teste com caminho para documento válido
    public void test1(){
        assertEquals(true,fm.insertFile("DOC.txt"));
    }

    @Test//Teste com caminho inválido
    public void test2(){
        assertEquals(false,fm.insertFile("DO.txt"));
    }
    @Test//Teste de remoção de digitos
    public void test3(){

        assertEquals("um dois tres",fm.removeDigits("um1 dois2 tres3"));
    }

}