# springboot-demo-bbs

1. ルートディレクトリに.envファイルを作成して、下記のように設定
```.env

SPRING_DATASOURCE_PASSWORD=yourPassword 
# yourPasswordは　利用環境のMySQLのrootユーザーのパスワードを自分で設定してください。

```
2. ターミナルで下記のコマンドを実行
```.bash
export SPRING_DATASOURCE_PASSWORD=yourPassword
```
3. その後springbootを起動
```.bash
./mvnw spring-boot:run  
```