/*
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
})(window, document, 'script', '//localhost:8080/vishy/scripts/vishy.analytics.js', 'vishy');

vishy('track', 'myElement', {
    id: '42',
    readKey: '%%vishy.readKey%%',
    writeKey: '%%vishy.writeKey%%',
    protocol: 'http',
    host: 'localhost',
    port: 8080,
    requestType: 'xhr'
});
</script>
*/

/*Lodamoijs 0.3.3*/
!function(a,b){"use strict";a.Lodamoi=b(a.document)}(this,function(a){"use strict";function b(b,c){var f=a.createElement(v);f.type=w;try{var g=a.createTextNode(b);f.appendChild(g)}catch(h){f.text=b}var i=d(f),j=e(c)?c:x;window.setTimeout(function(){j(),i()},1)}function c(b,c,f){var g=a.createElement(v);g.type=w,g.defer=g.async=f?!!f.async:!1,g.src=b;var h=e(c)?c:x,i=d(g,function(a){h(a),i()})}function d(b,c){if(!h(b))return x;var d=a.getElementsByTagName("head")[0]||a.documentElement;if(e(c)){var f=function(a){c(a)};b.readyState?b.onreadystatechange=function(a){("loaded"===b.readyState||"complete"===b.readyState)&&(b.onreadystatechange=null,f(a))}:b.addEventListener?b.addEventListener("load",f,!1):b.attachEvent&&b.attachEvent("load",f)}return d.firstChild?d.insertBefore(b,d.firstChild):d.appendChild(b),function(){d.removeChild(b)}}function e(a){return"function"==typeof a||!1}function f(a){return a&&"object"==typeof a&&"number"==typeof a.length&&"[object Array]"===Object.prototype.toString.call(a)||!1}function g(a){return"string"==typeof a}function h(a){return a&&a.nodeType===u||!1}function i(a){return h(a)&&j(a,v)}function j(a,b){return h(a)&&a.nodeName&&a.nodeName.toUpperCase()===b.toUpperCase()}function k(a){return i(a)&&g(a.src)&&a.src}function l(a){return i(a)&&""!==m(a)}function m(a){return h(a)?a.text||a.textContent||a.innerHTML||"":""}function n(a){throw new Error("Illegal argument"+(a?": "+a:"."))}function o(a){return g(a)&&/^(https?:\/\/|\/\/)[^ \t\r\n\v\f]+\.[^ \t\r\n\v\f]+$/.test(a)}function p(a){if(i(a))return[a];var b=[];if(h(a)&&a.childNodes.length>0)for(var c=a.getElementsByTagName(v),d=0,e=c.length>>>0;e>d;d++){var f=c[d];i(f)&&b.push(f)}return b}function q(a){a&&g(a)||n(),this._val=a}function r(a){a||n(),this._val=a,this._async=!1}function s(a){a&&h(a)||n(),this._val=a,this._async=!!a.async}function t(a){return this instanceof t?(a||n(),void(this._values=f(a)?a:[a])):new t(a)}var u=1,v="script",w="text/javascript",x=function(){};return q.prototype={async:function(){return this},load:function(a){var c=e(a)?a:x;b(this._val,c)}},r.prototype={async:function(){return this._async=!0,this},load:function(a){var b=e(a)?a:x;c(this._val,b,{async:this._async})}},s.prototype={async:function(){return this._async=!0,this},load:function(a){var d=e(a)?a:x,f=this._val;i(f)?k(f)?c(f.src,d,{async:this._async}):l(f)&&b(m(f),d):new t(p(f)).load(d)}},t._addElementToDom=d,t.code=function(a){return new q(a)},t.url=function(a){return new r(a)},t.tag=function(a){return new s(a)},t.prototype={load:function(a){var b=e(a)?a:x;if(0===this._values.length)return void b();for(var c=this._values.length,d={loadCount:0,totalRequired:c,onLoad:function(){this.loadCount++,this.loadCount===this.totalRequired&&b()}},f=function(a){d.onLoad(a)},i=0;c>i;i++){var j=this._values[i];h(j)?t.tag(j).load(f):o(j)?t.url(j).load(f):g(j)?t.code(j).load(f):f()}}},t});

(function(window, document, VishyObjectName, $http) {
    var vishy = window[VishyObjectName];

    var jsonHttp = {
        post: function(src, data) {
            return $http.post(src, JSON.stringify(data));
        }
    };

    var newMonitor = function(VisSense, elementId, config) {
        var element = document.getElementById(elementId);
        var visobj = VisSense.of(element, {
            hidden: 0.49
        });
        var monitor = VisSense.Client.Vishy(config, jsonHttp)
            .monitors({
                projectId: elementId
            }).custom(visobj, {
                interval: 1000,
                throttle: 100,
                inactiveAfter: 60 * 1000
            });

        monitor.on('visibility-initial-request', function(monitor, data) {
            console.table(data);
        });
        monitor.on('visibility-status-50/1', function(monitor, data) {
            console.table(data);
        });
        monitor.on('visibility-time-report', function(monitor, data) {
            console.table(data);
        });

        (function registerStopOnBeforeUnloadEventHandler() {
            var removeBeforeUnloadStopCallback = function() {};
            monitor.on('start', function() {
                removeBeforeUnloadStopCallback = (function () {
                    var eventId = addEventListener('beforeunload', function () {
                        monitor.stop();
                    }, true);

                    return function () {
                        removeEventListener('beforeunload', eventId, true);
                    }
                })();
            });
            monitor.on('stop', function() {
                removeBeforeUnloadStopCallback();
            });
        }());

        return monitor;
    };

    var createMonitor  = function(VisSense, elementId, config) {
        var Utils = VisSense.Utils;
        var vishyConfig = Utils.extend(config, {
            id: '42',
            readKey: '%%vishy.readKey%%',
            writeKey: '%%vishy.writeKey%%',
            protocol: 'http',
            host: 'localhost',
            port: 8080,
            requestType: 'xhr'
        });

        return newMonitor(VisSense, elementId, vishyConfig);
    };

    var setup = function(VisSense) {
        var Utils = VisSense.Utils;
        var monitors = [];

        Utils.forEach(vishy.q, function(args) {
            if (Utils.isArray(args) || args.length === 0) {
                console.log('Illegal arguments.', args);
                return;
            }

            var action = args[0];
            if(action === 'init') {
                console.log('Unimplemented operation.', 'init');
            }
            else if(action === 'create') {
                try {
                    var monitor = createMonitor(VisSense, args[1], args[2]);
                    monitors.push(monitor);
                } catch(e) {
                    console.log('MonitorCreationError', e);
                }
            }
            else if(action === 'start') {
                try {
                    var callback = Utils.isFunction(args[1]) ? args[1] : Utils.noop;
                    Utils.forEach(monitors, function(monitor) {
                       monitor.start();
                       callback(monitor);
                    });
                } catch(e) {
                    console.log('MonitorStartError', e);
                }
            }
            else {
                console.log('Unsupported operation.', args);
            }
        });
    };

    Lodamoi(['//rawgit.com/vissense/vissense/0.8.0/dist/vissense.min.js']).load(function() {
        Lodamoi([
            '//rawgit.com/vissense/vissense-percentage-time-test/0.5.0/dist/vissense-percentage-time-test.min.js',
            '//rawgit.com/vissense/vissense-metrics/0.1.0/dist/vissense.metrics.min.js',
            '//rawgit.com/vissense/vissense-user-activity/0.3.0/dist/vissense-user-activity.min.js',
            '//localhost:8080/static/vishy/scripts/vissense-simple-client-monitor/index.js',
            '//localhost:8080/static/vishy/scripts/vissense-simple-vishy-client-monitor/index.js'
        ]).load(function() {
            var VisSense = window.VisSense;
            setup(VisSense);
        });
    });

}(this, document, VishyObject, (function () {
    var noop = function() {};
    var asParamsArray = function (req) {
        return [req, req.responseText];
    };

    var xhr = function (type, url, data) {
        var methods = {
            success: noop,
            error: noop
        };
        var request = new XMLHttpRequest();
        request.withCredentials = true;
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                if (request.status >= 200 && request.status < 400) {
                    methods.success.apply(methods, asParamsArray(request));
                } else {
                    methods.error.apply(methods, asParamsArray(request));
                }
            }
        };
        request.open(type, url, true);

        request.send(data);

        return {
            then: function(success, error) {
                methods.success = success;
                methods.error = error;
                return methods;
            },
            success: function (callback) {
                methods.success = callback;
                return methods;
            },
            error: function (callback) {
                methods.error = callback;
                return methods;
            }
        };
    };

    return {
        get: function (src) {
            return xhr('GET', src, null);
        },
        post: function (src, data) {
            return xhr('POST', src, data);
        }
    };
}())));
