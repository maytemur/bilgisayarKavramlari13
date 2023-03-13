/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bilgisayarkavramlari13;

/**
 *
 * @author maytemur Java Thread'ler (İşletim Sistemleri 7, Java Eğitim Serisi 9.
 * Video) Nesne Yönelimli Programlama (Java ile) serisinin 13ncü videosu aynı
 * zamanda işletim sistemleri (operating systems) serisinin 7nci videosu
 */
public class Bilgisayarkavramlari13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //2 tane thread create edelim
        //aşağıdaki data yı burada da create edelim
        data d = new data(); //burada new ile yarattığımız gerçek obje diğerleri buna refere
        //eden null dır başlangıç olarak
        d.deger = 100;
        producer p = new producer(d);
        consumer c = new consumer(d);
        Thread t = new Thread(c);
        p.start();
        t.start();
    }

    //1nci yöntem extend etmek
    static class producer extends Thread {

        data d;

        public producer(data d) {
            this.d = d;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (d) {
                    d.deger++;
                    System.out.println("producer üretici " + d.deger);
                }
            }
        }
    }

    //2nci yöntem Runnable metodunu implement edip overwrite etmek
    static class consumer implements Runnable {

        //bunda run fonksiyonu override etmek zorundayız
        //özel birşey yapmıycaz ama object referrer kullanayım diyerek bir data class oluşturdu
        //Java da pointer olmadığı için bir objeye aynı anda birden fazla yerden erişebilmek
        //için object referrer kullanabiliriz
        public void run() {
            for (int i = 0; i < 10; i++) {
                d.deger--;
                System.out.println("consumer tüketici " + d.deger);
            }
        }
        data d;

        public consumer(data d) {
            this.d = d;
        }
    }

    //şurada aptal bir data class oluşturalım dedi!
    static class data {

        int deger;
    }
    //thread olayı data sharing demektir yani aynı dataya birden fazla yerden müdahale edilir
    //burdada biri datayı artırırken diğeri azaltıyor
    //burada extend yöntemi yerine implement etmeyi tavsiye ediyor çünkü javada 
    //multiple inheretence olmadığından 1 hakkımız var onuda Thread extend ederek harcamak yerine
    //böyle dolaylı yollardan implement ederek yapmak daha iyi dedi ,sonradan docs lara bakılabilir?

    //bunu çalıştırınca gördük ki 1 defa da olsa arada sonuc 99 çıktı ,peki bu nasıl oldu?
    //10 defa artırıp 10 defa eksiltince 100 de bitmesi gerekmiyor muydu ?
    //genelde böyle oluyor ama arada çok ender de olsa 99 da bitebiliyor buna thread ler arasında
    //race condition da operating system da anlatılmıştı bu atomik durum bazen oluşabilir
    //ve 100 defa doğru çalışır ama 1 defa da olsa sonuç 99 çıkabilir
    //bunu java da bu değişken bölgesine critical insection deniyor şöyle yapılıyor;
    //engellemek tek bir müdahale olması için critical insection bölgesinde synchronized(d)
    //diyerek bu değişkene tek bir yerden müdahale olmasını sağlıyabiliriz dedi
}
