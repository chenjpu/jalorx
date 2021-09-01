job "${DRONE_REPO_OWNER}-${DRONE_REPO_NAME}" {
  datacenters = ["dc1"]
  type        = "service"
  namespace   = "${PLUGIN_NAMESPACE}"

  update {
    max_parallel     = 1
    min_healthy_time = "10s"
    healthy_deadline = "3m"
    auto_revert      = true
    auto_promote     = true
    canary           = 1
  }

  reschedule {
    attempts       = 15
    interval       = "1h"
    delay          = "15s"
    delay_function = "exponential"
    max_delay      = "120s"
    unlimited      = false
  }

  group "service" {
    restart {
      attempts = 5
      interval = "3m"
      delay    = "6s"
      mode     = "fail"
    }
    
    scaling {
      enabled = true
      min = 1
      max = 5
      policy {
      }
    }

	meta {
		COMMIT_SHA = "${DRONE_COMMIT_SHA}"
	}
		
    task "service" {
      driver = "docker"
      
      
      config {
        image = "hub.service.consul:5000/openjdk:jdk8"
        command = "java"
		
		    args = [
		         "-Dmicronaut.environments=sit",
				 "-jar","local/jalorx-app.jar"
		    ]

        port_map {
          http = 8080
        }
      }

      artifact {
        source = "http://art.service.consul:2103/${DRONE_REPO_OWNER}/${DRONE_REPO_NAME}.tar.gz"
      }

      service {
        name = "${DRONE_REPO_NAME}"

        tags = [
          "loki",
          "lba.enable=true",
          "lba.weight=100",
          "lba.http.routers.${DRONE_REPO_NAME}.entrypoints=http",
        ]

        canary_tags = [
          "canary",

          "lba.enable=true",
          "lba.weight=1",
          "lba.http.routers.${DRONE_REPO_NAME}.entrypoints=http",
        ]
        
        meta {
          alloc_id="${NOMAD_ALLOC_ID}"
          app_code="${meta.app_code}"
          env="${meta.env}"
        }

        address_mode = "host"
        port         = "http"

        check {
          type           = "http"
          port           = "http"
          path           = "/services/info"
          interval       = "12s"
          timeout        = "6s"
          initial_status = "warning"

          check_restart {
            limit           = 3
            grace           = "40s"
            ignore_warnings = true
          }
        }
      }

      resources {
        cpu    = 200
        memory = 512

        network {
          port "http" {}
        }
      }
    }
  }
}
