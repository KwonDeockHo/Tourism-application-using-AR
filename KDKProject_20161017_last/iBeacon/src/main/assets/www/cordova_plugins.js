cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/org/www/org.underscorejs.underscore/underscore.js",
        "id": "org.underscorejs.underscore"
    },
    {
        "file": "plugins/org/www/org.bcsphere/bc.js",
        "id": "org.bcsphere.bcjs"
    },
    {
        "file": "plugins/org/www/org.bcsphere.bluetooth/bluetoothapi.js",
        "id": "org.bcsphere.bluetooth.bluetoothapi",
        "merges": [
            "navigator.bluetooth"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.profile/proximity.js",
        "id": "org.bluetooth.profile.proximity",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.profile/find_me.js",
        "id": "org.bluetooth.profile.find_me",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.profile/serial_port.js",
        "id": "org.bluetooth.profile.serial_port",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.service/battery_service.js",
        "id": "org.bluetooth.service.battery_service",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.service/blood_pressure.js",
        "id": "org.bluetooth.service.blood_pressure",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.service/health_thermometer.js",
        "id": "org.bluetooth.service.health_thermometer",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.service/immediate_alert.js",
        "id": "org.bluetooth.service.immediate_alert",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.service/link_loss.js",
        "id": "org.bluetooth.service.link_loss",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.service/serial_port.js",
        "id": "org.bluetooth.service.serial_port",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org/www/org.bluetooth.service/tx_power.js",
        "id": "org.bluetooth.service.tx_power",
        "merges": [
            "BC"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.dialogs/www/notification.js",
        "id": "org.apache.cordova.dialogs.notification",
        "merges": [
            "navigator.notification"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.dialogs/www/android/notification.js",
        "id": "org.apache.cordova.dialogs.notification_android",
        "merges": [
            "navigator.notification"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org": "0.5.0",
    "org.apache.cordova.dialogs": "0.2.11"
}
// BOTTOM OF METADATA
});