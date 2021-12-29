//Sair (LOGOUT):
document.querySelector("#logout").addEventListener("click", (e) => {
	e.preventDefault();
	
	document.querySelector("form[name='frmLogout'] input[name='logout']").value = "LOGOUT";
	
	document.querySelector("form[name='frmLogout']").submit();
});



//Remoção do evento de click oriundo da tag de link no logotipo da ASN:
document.querySelector("nav ul li:nth-child(1) a").addEventListener("click", (e) => {
    e.preventDefault();
});



//Menu toggle:
document.querySelector(".toggle").addEventListener("click", toggleMenu);

function toggleMenu() {
    document.querySelector(".toggle").classList.toggle("active");
    document.querySelector("nav").classList.toggle("active");
    document.querySelector(".main").classList.toggle("active");
}



//Filtros:
if (document.querySelector(".filters")) {
    document.querySelector(".filterIcon").addEventListener("click", toggleFiltro);
    document.querySelector(".filters .close img").addEventListener("click", toggleFiltro);
}

function toggleFiltro() {
    document.querySelector(".filters").classList.toggle("active");
}

if (document.querySelector(".filters")) {
    document.querySelector(".filters form select").addEventListener("change", showPeriodFields);
}

function showPeriodFields() {
    if (document.querySelector(".filters form select").value == document.querySelectorAll(".filters form select option").length - 1) {
        document.querySelector(".filters form .period").style.display = "flex";
		document.querySelector("input[name='dataIn']").required = true;
		document.querySelector("input[name='dataFin']").required = true;
    } else {
        document.querySelector(".filters form .period").style.display = "none";
		document.querySelector("input[name='dataIn']").required = false;
		document.querySelector("input[name='dataFin']").required = false;
    }
}



//Responsividade dos graficos do DashBoard:
if (document.querySelector(".graphBox")) {

    if (window.innerWidth <= 1130 && window.innerWidth > 980) {
        let cvs = Array.from(document.querySelectorAll("canvas"));
        cvs.forEach((cv) => {
            cv.width = "1";
            cv.height = "1";
        });
    
        drawCharts();
    } else if (window.innerWidth <= 720 && window.innerWidth > 560) {
        let cvs = Array.from(document.querySelectorAll("canvas"));
        cvs.forEach((cv) => {
            cv.width = "1";
            cv.height = "1";
        });
    
        drawCharts();
    } else if (window.innerWidth <= 560 && window.innerWidth > 450) {
        let cvs = Array.from(document.querySelectorAll("canvas"));
        cvs.forEach((cv) => {
            cv.width = "4";
            cv.height = "5";
        });
    
        drawCharts();
    } else if (window.innerWidth <= 450 && window.innerWidth > 382) {
        let cvs = Array.from(document.querySelectorAll("canvas"));
        cvs.forEach((cv) => {
            cv.width = "3";
            cv.height = "4";
        });
    
        drawCharts();
    } else if (window.innerWidth <= 382) {
        let cvs = Array.from(document.querySelectorAll("canvas"));
        cvs.forEach((cv) => {
            cv.width = "2";
            cv.height = "3";
        });
    
        drawCharts();
    } else {
        let cvs = Array.from(document.querySelectorAll("canvas"));
        cvs.forEach((cv) => {
            cv.width = "2";
            cv.height = "1";
        });
    
        drawCharts();
    }
    
}



//Cores (aplicadas com efeito degradê) dos itens (Ordens de Serviços)
let itemsOrdensServicos = Array.from(document.querySelectorAll(".ordensdeservico"));

itemsOrdensServicos.forEach((item, index) => {
    let opacityColor = (itemsOrdensServicos.length - index) * (1 / itemsOrdensServicos.length);

    item.style.background = "rgba(253, 170, 34, "+opacityColor+")";
});