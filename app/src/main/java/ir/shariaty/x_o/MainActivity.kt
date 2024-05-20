package ir.shariaty.x_o

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ir.shariaty.x_o.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playOfflineBtn.setOnClickListener {
            createOfflineGame();
        }
        binding.playOnlineBtn.setOnClickListener {
            createOnlineGame();
        }
        binding.joinOnlineBtn.setOnClickListener {
            joinOnlineGame();
        }
    }

    private fun joinOnlineGame() {
        var gameId = binding.gameIdInput.text.toString()
        if(gameId.isEmpty()){
            binding.gameIdInput.setError("please inter ID!")
            return
        }
            GameData.myID="O"
            Firebase.firestore.collection("games")
                .document(gameId)
                .get()
                .addOnSuccessListener {
                    val model = it?.toObject(GameModel::class.java)
                    if (model == null){
                        binding.gameIdInput.setError("plese enter correct ID")
                    }else{
                        model.gameStatus = GameStatus.JOINED
                        GameData.saveGameModel(model)
                        startGame()

                }
        }
    }

    fun createOfflineGame() {
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED
            )
        )
        startGame()
    }

    fun createOnlineGame(){
        GameData.myID = "X"
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.CREATED,
                gameId = Random.nextInt(1000,9999).toString()
            )
        )
        startGame()
    }

    fun startGame() {
        startActivity(Intent(this,GameActivity::class.java))
    }
}


