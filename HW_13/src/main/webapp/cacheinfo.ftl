<html>
<head>
    <title>HW13</title>
    <script type="javascript">
        init = function() {
            var ws;
            ws = new WebSocket("ws://localhost:8080/cacheinfo")
            ws.onopen = function (event) {
            }

            var cacheinfo;

            function () {

            }

            ws.onmessage = function (event) {
                var cacheinfo = JSON.parse(event.data);
                document.getElementById("miss").value = cacheinfo.miss;
                document.getElementById("hit").value = cacheinfo.hit;
                document.getElementById("capacity").value = cacheinfo.capacity;
                document.getElementById("cachesize").value = cacheinfo.cachesize;
                document.getElementById("idletime").value = cacheinfo.idletime;
                document.getElementById("lifetime").value = cacheinfo.miss;
                document.getElementById("eviction").value = cacheinfo.eviction;
            }

            ws.onclose = function (event) {
            }
        }

        function sendCacheQuery(){
            ws.send("getCacheInfo");
        }
    </script>

    <body onload='init()'>
        <h2>LRU Cache info</h2>
        <h4 id="miss">Miss:${miss}</h4>
        <h4 id="hit">Hit:${hit}</h4>
        <h4 id="capacity">Capacity:${capacity}</h4>
        <h4 id="cachesize">Cache size:${cacheSize}</h4>
        <h4 id="eviction">Eviction:${eviction}</h4>
        <h4 id="idletime">Idle time (ms):${idle}</h4>
        <h4 id="lifetime">Life time (ms):${life}</h4>
    </body>
</html>