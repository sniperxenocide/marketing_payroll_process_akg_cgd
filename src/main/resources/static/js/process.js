function init() {
    setHeader();
    setFooter();
    menuButtonColorChange(2);
    updateStatus();
}

function updateStatus(){
    let callCount = 0;
    let callAgain = true;
    setInterval(() => {
        if(callAgain){
            callAPI(baseURL+'/process/status',GET,null,statusCallback,false);
        }
    },1000)
    function statusCallback(response){
        callCount++;
        try {
            let json = JSON.parse(response);
            if(json['status']){
                //console.log(json);
                json = json['data'];
                document.getElementById('processed_employee').innerText = json['processedEmployee'];
                document.getElementById('time_passed').innerText = json['timePassed'];
                document.getElementById('progress').innerText = json['percent'];
                document.getElementById('time_left').innerText = json['timeToEnd'];
                document.getElementById('progress_bar').style.width
                    = json['percent'].toString().replace(' ','');
            }
            else {
                callAgain=false;
                if(callCount>1) location.replace(location.origin+'/process');
            }
        }catch (e){console.log(e)}
    }
}

