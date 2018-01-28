<html>
<head>
    <title>HW13</title>
    <script>
        var ws;
        ws = new WebSocket("ws://localhost:8080/cinfo")
        ws.onopen = function (event) {

        }
    </script>

    <body onload='setInterval(function(){refresh();}, 2000)'>
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