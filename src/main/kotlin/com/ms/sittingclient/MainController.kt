package com.ms.sittingclient

import com.sitting.client.repository.MeasurementRepository
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.springframework.stereotype.Component

import java.io.IOException
import java.time.LocalDateTime
import java.util.Collections
import java.util.logging.Logger

@Component
class MainController(private val measurementsThread: MeasurementsThread,
                     private val measurementRepository: MeasurementRepository) : MeasurementObservable {
    private val logger = Logger.getLogger(javaClass.simpleName)

    @FXML
    var connectionStatusImage: ImageView? = null
    private val connectionStatus = SimpleObjectProperty<Image>()

    @FXML
    var backTopLeftStatusImage: ImageView? = null
    private val backTopLeftStatus = SimpleObjectProperty<Image>()

    @FXML
    var backTopCenterStatusImage: ImageView? = null
    private val backTopCenterStatus = SimpleObjectProperty<Image>()

    @FXML
    var backTopRightStatusImage: ImageView? = null
    private val backTopRightStatus = SimpleObjectProperty<Image>()

    @FXML
    var backBottomLeftStatusImage: ImageView? = null
    private val backBottomLeftStatus = SimpleObjectProperty<Image>()

    @FXML
    var backBottomCenterStatusImage: ImageView? = null
    private val backBottomCenterStatus = SimpleObjectProperty<Image>()

    @FXML
    var backBottomRightStatusImage: ImageView? = null
    private val backBottomRightStatus = SimpleObjectProperty<Image>()

    @FXML
    var bottomTopLeftStatusImage: ImageView? = null
    private val bottomTopLeftStatus = SimpleObjectProperty<Image>()

    @FXML
    var bottomTopCenterStatusImage: ImageView? = null
    private val bottomTopCenterStatus = SimpleObjectProperty<Image>()

    @FXML
    var bottomTopRightStatusImage: ImageView? = null
    private val bottomTopRightStatus = SimpleObjectProperty<Image>()

    @FXML
    var bottomBottomLeftStatusImage: ImageView? = null
    private val bottomBottomLeftStatus = SimpleObjectProperty<Image>()

    @FXML
    var bottomBottomCenterStatusImage: ImageView? = null
    private val bottomBottomCenterStatus = SimpleObjectProperty<Image>()

    @FXML
    var bottomBottomRightStatusImage: ImageView? = null
    private val bottomBottomRightStatus = SimpleObjectProperty<Image>()

    @FXML
    var positionSummaryImage: ImageView? = null
    private val positionSummary = SimpleObjectProperty<Image>()

    @FXML
    fun initialize() {
        measurementsThread.start(this, 1000)

        initDataBinding()
    }

    private fun initDataBinding() {
        connectionStatusImage!!.imageProperty().bindBidirectional(connectionStatus)

        backTopLeftStatusImage!!.imageProperty().bindBidirectional(backTopLeftStatus)
        backTopCenterStatusImage!!.imageProperty().bindBidirectional(backTopCenterStatus)
        backTopRightStatusImage!!.imageProperty().bindBidirectional(backTopRightStatus)

        //TODO write rest
    }

    fun onNext(measurement: Measurement, assessment: Boolean?) {
        //TODO changing images
    }

    override fun onError(throwable: Throwable) {
        //TODO changing images
    }

    //TODO only temporary
    @FXML
    fun sendFakeInfo(actionEvent: ActionEvent) {
        measurementRepository.save(Measurement(LocalDateTime.now(),
                emptyList<T>()))

    }
}
