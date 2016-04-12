import java.util.Comparator;


/**Compares the scores in the list for sorting
 * @param score1
 * @param score2
 * @return
 */
public class ScoreComparator implements Comparator<Score>
{
        public int compare(Score score1, Score score2) {

            int sc1 = score1.getScore();
            int sc2 = score2.getScore();

            if (sc1 > sc2)
            {
                return -1;
            }
            else if (sc1 < sc2)
            {
                return +1;
            }
            else
            {
                return 0;
            }
        }
}