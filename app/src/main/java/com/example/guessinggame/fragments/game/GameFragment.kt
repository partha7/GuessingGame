
package com.example.guessinggame.fragments.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.guessinggame.R
import com.example.guessinggame.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)


        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord.toString()
        })
        viewModel.currtime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })


        viewModel.hasGameFinished.observe(viewLifecycleOwner, Observer { hasGameFinished ->
            if(hasGameFinished){
                gameFinished()
                viewModel.gameFinishedOver()
            }
            println("Not yet over")
        })

        return binding.root

    }



    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }





    /** Methods for updating the UI **/

//    private fun updateWordText() {
//        binding.wordText.text = viewModel.word
//
//    }

//    private fun updateScoreText() {
//        binding.scoreText.text = viewModel.score.toString()
//    }
}
