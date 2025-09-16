
    $(document).ready(function () {
    // Click Next
    $('.next-step').on('click', function () {
        let activeTab = $('.nav-tabs .nav-link.active');
        let nextTab = activeTab.parent().next().find('.nav-link');
        if (nextTab.length) {
            nextTab.trigger('click');
        }
    });

    // Click Previous
    $('.prev-step').on('click', function () {
        let activeTab = $('.nav-tabs .nav-link.active');
        let prevTab = activeTab.parent().prev().find('.nav-link');
        if (prevTab.length) {
            prevTab.trigger('click');
        }
    });
});

