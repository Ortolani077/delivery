function adjustGallery() {}
document.addEventListener("DOMContentLoaded", function() {
    const e = document.querySelectorAll(".gallery__item"),
    n = document.getElementById("lightbox"),
    o = n.querySelector("img");
    e.forEach(t => {
        t.addEventListener("click", () => {
            var e = t.querySelector(".gallery__img").src;
            o.src = e,
            n.style.display = "flex"
        })
    }),
    n.addEventListener("click", () => {
        n.style.display = "none"
    }),
    window.addEventListener("resize", () => {
        adjustGallery()
    })
}),
$(document).ready(function() {
    $(".carousel").slick({
        dots: !0,
        infinite: !0,
        slidesToShow: 4,
        slidesToScroll: 4,
        autoplay: !0,
        autoplaySpeed: 2e3
    }),
    M.AutoInit()
}),
document.addEventListener("DOMContentLoaded", function() {
    document.addEventListener("click", function(e) {
        e.target.closest(".expanded-content") || e.target.closest(".custom-btn") || document.querySelectorAll(".expanded-content").forEach(e => {
            e.classList.remove("show")
        })
    }),
    document.querySelectorAll(".custom-btn").forEach(e => {
        e.addEventListener("click", function() {
            var e = this.getAttribute("data-bs-target").substring(1);
            const t = document.getElementById(e);
            document.querySelectorAll(".expanded-content").forEach(e => {
                e !== t && e.classList.remove("show")
            }),
            t.classList.toggle("show")
        })
    }),
    adjustGallery()
});