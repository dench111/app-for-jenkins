import os
from minio import Minio
from minio.error import S3Error

rootdir = 'C:/Work/minio_tmp'
all_count = 0
empty_count = 0
for root, dirs, files in os.walk(rootdir):
    dirslist = dirs
    for dir in dirslist:
        files = os.listdir(f'{rootdir}/{dir}')
        all_count = all_count + 1
        print('\n' + str(all_count) + ' / ' + str(len(dirslist)), dir)
        if len(files) > 0:
            client = Minio('ip:9000',
                           access_key='minio',
                           secret_key='minio123',
                           secure=False)
            for filename in files:
                filepath = f'{rootdir}/{dir}/{filename}'
                try:
                    result = client.fput_object(dir, filename, filepath)
                    print("created {0} object;etag: {1}, version-id: {2}".format(result.object_name, result.etag, result.version_id))
                except ResponseError as err:
                    print(err)
        else:
            print('Bucket ' + dir + ' пустой и не будет загружен в minio')
            empty_count = empty_count + 1
    result_count = all_count - empty_count
print('\n Всего каталогов (бакетов) обработано: ' + str(all_count) + ' из них загружено на сервер: ' + str(result_count) + ' пустых: ' + str(empty_count))
