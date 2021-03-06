package dataimport;

import java.util.HashMap;
import java.util.Map;

public class GymsResponse {
    private Map<String, GymResponse> gyms = new HashMap<>();

    public GymsResponse() {
    }

    public GymsResponse(Map<String, GymResponse> valueMap) {
        setGyms(valueMap);
    }

    public Map<String, GymResponse> getGyms() {
        return gyms;
    }

    public void setGyms(Map<String, GymResponse> gyms) {
        this.gyms = gyms;
    }

    /**
     {"1018964":{"raid_status":0,"raid_timer":0,"raid_level":0,"lure_timer":0,"z3iafj":"MTEwOTUyMzQ3Ljc=","f24sfvs":"MzE4NzY1ODYuOTY=","g74jsdg":"MA==","xgxg35":"MQ==","y74hda":"MQ==","zfgs62":"MTAxODk2NA==","rgqaca":"jarlasa-barhus","rfs21d":"J\u00e4rl\u00e5sa B\u00e5rhus"},"49424655":{"raid_status":0,"raid_timer":0,"raid_level":0,"lure_timer":0,"z3iafj":"MTEwOTUxMTEyLjQxNg==","f24sfvs":"MzE4NzA4NjIuNDI4","g74jsdg":"MA==","xgxg35":"MQ==","y74hda":"MQ==","zfgs62":"NDk0MjQ2NTU=","rgqaca":"jarlasa-milsten","rfs21d":"J\u00e4rl\u00e5sa Milsten"},"118412":{"raid_status":0,"raid_timer":0,"raid_level":0,"z3iafj":"MTEwOTUxODg4LjQwNA==","f24sfvs":"MzE4NzgyNTkuMzE2","g74jsdg":"MQ==","xgxg35":"Mg==","y74hda":"Mg==","zfgs62":"MTE4NDEy","rgqaca":"jarlasa-kyrka","rfs21d":"J\u00e4rl\u00e5sa Kyrka"}}
     */
}
