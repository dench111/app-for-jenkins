from minio import Minio
import os, re
from minio import Minio

# client = Minio('host:9000',
#                access_key='login',
#                secret_key='password',
#                secure=False)

buckets = client.list_buckets()
#print(buckets)

for bucket in buckets:
    bucketname = bucket.name
    print('**********\n bucketname = ' + bucket.name, bucket.creation_date)
    catalogname = str(bucket.name)
    print('**********\n Создаю каталог по имени бакета:' + catalogname)
    os.mkdir(f'C:/Work/minio_tmp/{catalogname}')
    objects = client.list_objects(bucketname, recursive=True)
    for obj in objects:
        objectname = obj.object_name.encode('utf-8')
        convfilename = str(objectname)
        filenameregex = re.compile(r"'(.+?)'")
        filenamesearch = filenameregex.search(convfilename)
        filename = filenamesearch.group(1)
        print('**********\n filename = ' + str(filename))
        filepath=f'C:/Work/minio_tmp/{catalogname}/{filename}'
        #print('filepath=' + filepath)
        print('**********\n objectname=' + obj.bucket_name, obj.object_name.encode('utf-8'), obj.last_modified,
              obj.etag, obj.size, obj.content_type)
        try:
            client.fget_object(bucketname, objectname, filepath)
        except ResponseError as err:
            print(err)
