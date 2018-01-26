<html>
<head>
    <title>HW12</title>
    <script>
        function refresh() {
            location.reload();
        }
    </script>

    <body onload='setInterval(function(){refresh();}, 2000)'>
        <h2>LRU Cache info</h2>
        <h4>Miss:${miss}</h4>
        <h4>Hit:${hit}</h4>
        <h4>Capacity:${capacity}</h4>
        <h4>Cache size:${cacheSize}</h4>
        <h4>Eviction:${eviction}</h4>
        <h4>Idle time (ms):${idle}</h4>
        <h4>Life time (ms):${life}</h4>
    </body>
</html>