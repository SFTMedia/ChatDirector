FROM redis
COPY "./test/redis/redis.conf" /usr/local/etc/redis/redis.conf
CMD ["redis-server","/usr/local/etc/redis/redis.conf"]