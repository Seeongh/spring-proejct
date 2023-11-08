<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>hellow</title>
</head>
<style>
</style>
<body style="background-color:rgb(232,241,255)">
<!--rgb(E8,FF,F1)--!>
<div id="root" class="bioWrap">
    <div class="App" style>
        <div id="BioHead" class="header_frag">
            <div class="inner_header">
                <h1 class="tit_header">  </h1>
           </div>
        </div>
        <main id="BioInfo">
            <main id="BioInfoContent" class="bioInfoContent">
                <div id="Logo">
                    <div class="logo">
                        <h2>    MZ HealthCare   </h2>
                    </div>
                    <div class="notice">
                        <div class="search"> </div>
                    </div>
               </div>
                <div id="bio-graph">
                    <div class="graph-rhythm">
                        <div class="biorhythm">
                        </div>
                    </div>

                    <div class="graph-rhythm">
                        <div class="biorhythm">
                        </div>
                    </div>
                </div>
                <div class="banner">
                    <div class="banner-content">
                    </div>
                </div>

                <div class="list">
                    <div class="user-list">
                    </div>
                </div>
            </main>
            <main id="BioMyContent" class="bioMyContent">
                <div class="myInformation">
                    <div class="myProfile">
                        <h3> Hellow, James</h3>
                    </div>
                </div>
                <div class="target">
                    <div class="graph-rhythm">
                        <div class="biorhythm">
                        </div>
                    </div>
                </div>
                <div class="grade">
                    <div class="myGrade">
                    </div>
                </div>
            </main>
        </main>
    </div>
</div>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    #BioHead {
        width : 10vw;
        height: 100vh;
        float: left;
        display: flex;
        justify-content: center;
        align-items: center;
    }
	.inner_header {
        box-sizing: border-box;
        background-color: white;
        border-radius: 25px;
        height : 92vh;
        width: 9vh;
	}

    #BioInfo {
        width : 87vw;
        height: 100vh;
        float: left;
        display: flex;
        justify-content: center;
        align-items: center;
    }

	#BioInfoContent {
        width : 58vw;
        height: 92vh;
        float: left;
	}

	#BioMyContent {
        width : 29vw;
        height: 92vh;
        float: left;
	}

	#Logo{
	    width: 58vw;
	    height: 8vh;
	    float: top;
	    display: flex;
        justify-content: center;
        align-items: center;
	}
    .logo{
        width: 20vw;
        height: 8vh;
        padding-left: 2vw;
        padding-top: 3vh;
	}
	.notice {
        width: 38vw;
        height: 8vh;
	}
	.search {
        width: 37vw;
        height: 4vh;
        background-color: white;
        border-radius: 25px;
        margin-top:3vh;
	}

	#bio-graph {
	    width: 58vw;
        height: 38vh;
        display: flex;
        float:top;
	}
	.graph-rhythm {
        width: 29vw;
        height: 38vh;
        float:left;
        display: flex;
        justify-content: center;
        align-items: center;
	}
    .biorhythm {
        width : 25vw;
        height: 30vh;
        background-color: white;
        border-radius: 25px;
    }


    .banner {
        width: 58vw;
        height: 17vh;
        display: flex;
        float:top;
        justify-content: center;
        align-items: center;
    }
    .banner-content {
        width: 55.5vw;
        height: 16vh;
        display: flex;
        background-color: white;
        border-radius: 25px;
    }


    .list {
        width: 58vw;
        height: 29vh;
        display: flex;
        float:top;
        justify-content: center;
        align-items: center;
    }
    .user-list{
        width: 57vw;
        height: 26vh;
        display: flex;
        background-color: white;
        border-radius: 25px;
    }


	.myInformation {
            width: 29vw;
            height: 8vh;
            float:top;
            display: flex;
            justify-content: center;
            align-items: center;
        	}
    .myProfile {
           padding-top: 3vh;
           width: 20vw;
           height: 8vh;
           text-align: right;
       }

    .grade {
        width: 29vw;
        height: 46vh;
        float:top;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .myGrade {
        width : 26vw;
        height: 46vh;
        background-color: white;
        border-radius: 25px;
    }


</style>
</body>
</html>