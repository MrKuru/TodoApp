# Todo App

### Başlangıç

Projeyi local ortamda çalıştırmak için aşağıdaki adımları takip edebilirsiniz..

### Gereksinimler

Projeyi çalıştırabilmek için şu araçlara ihtiyaç duyulmaktadır:
- Java JDK 17
- Couchbase 
- Docker

## Couchbase Kurulumu

Projenin çalıştırılabilmesi için local ortamda couchbase server ayağa kaldırıp, proje için gerekli konfigürasyonları yapmamız gerekiyor.

1. Terminalde aşağıdaki komutu kullanarak Couchbase image çekilir.
    ```
    docker pull couchbase:latest
    ```
   
2. Elde edilen image kullanılarak bir konteynır çalıştırılır.
    ```
    docker run -d --name db -p 8091-8094:8091-8094 -p 11210:11210 couchbase:latest
    ```
   
3. Web tarayıcı üzerinde http://localhost:8091 adresi açılır ve şu adımlar takip edilir..
- Cluster oluşturma
   - Setup New Cluster'a tıklıyoruz ve aşağıdaki konfigurasyonları uyguluyoruz ve next butonuna tıklıyoruz.
      - Cluster Name : hb
      - Create Admin Username : admin
      - Create Password : password
      - Confirm Password : password
   - Açılan sayfada  I accept the terms & conditions butonuna tıklayıp, Configure Disk,Memory,Services butonuna tıklıyoruz.
   - Açılan sayfada Save & Finish butonuna tıklıyoruz.
- Bucket oluşturma
   - Ekranın Sol sekmesinden Buckets seçilir ve ADD BUCKET'a tıklanarak hb isminde bir bucket oluşturulur.
     - Name : hb
- Scope oluşturma
  - Yaratılan hb ismindeki bucket üzerinde ekranın sağ tarafında Scopes & Collections butonuna tıklanır.
  - Açılan ekranda sağ üst köşede bulunan ADD SCOPE butonuna tıklanarak dev isminde yeni scope oluşturulur.
    - Cluster Name : hb
    - New Scope Name : dev
- Collection oluşturma
   - Yaratılan dev isimli scope üzerinde ekranın sağ tarafına doğru Ad Collection butonuna tıklanır ve üç tane sırasıyla account, todo, task isimlerine sahip collectionlar eklenir.
- Index oluşturma
   - Ekranın sol tarafında yer alan Query sekmesine tıklanır.
      - Query Editor alanına aşağıdaki komutlar tek tek yazılıp Execute butonuna basılır.
        ```
        CREATE INDEX `idx_account_id` ON `hb`.`dev`.`todo`(`aid`)
        ```
        ```
        CREATE INDEX `idx_todo_id` ON `hb`.`dev`.`task`(`tid`)
        ```
        ```
        CREATE INDEX `idx_username` ON `hb`.`dev`.`account`(`username`)
        ```
---
## Todo App Kurulumu

1. Todo app projesini aşağıdaki komutu kullanarak bir dizine klonlayın:
    ```
    git clone https://github.com/MrKuru/TodoApp.git
    ```
2. Projeyi TodoAppApplication class içerisindeki main metodu kullanarak başlatabilirsiniz.  
---
## Kullanım Ve Test

Projedeki endpointler Basic Authentication ile korunmaktadır. Test için postman ya da swagger kullanılabilir.
- Swagger Url : http://localhost:8080/swagger-ui/index.html#/
- Username : Melih
- Password : 12345
