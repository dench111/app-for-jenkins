alert=$ELKStatus
# Будет отображаться "От кого"
FROM=""
# Кому
MAILTO=""
# Тема письма
NAME="отчет о состоянии сервера elasticsearch"
# Тело письма
#BODY="состояние сервера $realstatus"
# В моем примере я отправляю письма через существующий почтовый ящик на gmail.com 
# Скрипт легко адаптируется для любых почтовых серверов
SMTPSERVER="smtp.gmail.com:587"
# Логин и пароль от учетной записи gmail.com
SMTPLOGIN=$SMTPLOGIN
SMTPPASS=$SMTPPASS
# Отправляем письмо
/usr/bin/sendEmail -f $FROM -t $MAILTO -o message-charset=utf-8  -u $NAME -m $alert -s $SMTPSERVER -o tls=yes -xu $SMTPLOGIN -xp $SMTPPASS
