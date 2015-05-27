vishy-server
===

## build and run
`mvn clean package && java -jar vishy-server-web/target/vishy-server-web-<version>.jar`

### docker
```
cd vishy-server-web/target
mvn docker:build
```
```
docker run -t
  --name openmrc-vishy
  --env-file src/main/docker/vishy_example_environment.env
  -p 8080:8080
  tbk/vishy-server-web
```

```html
<script async>

  (function (i, s, o, g, r, a, m) {
    i['VishyObject'] = r;
    i[r] = i[r] || function (key, name) {
      (i[r].q = i[r].q || []).push(arguments)
    }, i[r].l = 1 * new Date();
    a = s.createElement(o),
      m = s.getElementsByTagName(o)[0];
    a.async = 1;
    a.src = g;
    m.parentNode.insertBefore(a, m)
  })(window, document, 'script', '//localhost:8080/static/vishy-analytics/dist/vishy-analytics.min.js', 'vishy');

  vishy('init', 'UA-48540954-6', 'auto');

  vishy('create', 'myElement', {
    id: '42',
    readKey: '%%vishy.readKey%%',
    writeKey: '%%vishy.writeKey%%',
    protocol: 'http',
    host: 'localhost',
    port: 8080,
    requestType: 'xhr'
  });

  vishy('start', function(monitor) {
    console.log('monitor started');
  });
</script>
```